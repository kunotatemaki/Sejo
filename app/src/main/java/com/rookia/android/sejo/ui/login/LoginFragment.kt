package com.rookia.android.sejo.ui.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentLoginBinding
import com.rookia.android.sejo.framework.utils.FingerprintUtils
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PinScreen
import java.util.concurrent.Executor
import javax.inject.Inject


class LoginFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val fingerprintUtils: FingerprintUtils,
    private val biometricInfo: BiometricPrompt.PromptInfo,
    private val preferencesManager: PreferencesManager,
    private val resourcesManager: ResourcesManager
) : BaseFragment(R.layout.fragment_login), PinScreen.BiometricHelper,
    PinScreen.PinValidator {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt

    override fun needToShowBackArrow(): Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val pinSet = preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_PIN_SENT_TAG)
        val phoneValidated =
            preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_VALIDATED_PHONE_TAG)
        val personalInfo =
            preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_PERSONAL_INFO_TAG)
        if (pinSet.not() || phoneValidated.not() || personalInfo.not()) {
            navigateToRegisterFlow()
            activity?.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.loginPinScreen.apply {
            setBiometricHelper(this@LoginFragment)
            setPinValidator(this@LoginFragment)
            setHeader(resourcesManager.getString(R.string.fragment_login_title))
        }
        viewModel = injectViewModel(viewModelFactory)

        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    login()

                }
            })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        viewModel.storeToken(it.data?.token)
                        navigateToDashboard()
                    }
                    Result.Status.ERROR -> {
                        hideLoading()
                        binding.loginPinScreen.showError()
                    }
                    Result.Status.LOADING -> showLoading()
                }
            }
        })



    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            if (shouldShowFingerPrintScreen() && binding.loginPinScreen.getPin().isBlank()) {
                showBiometricsLogin()
            }
        }, 300)
    }

    override fun onPinChanged(pin: String, isCompleted: Boolean) {
        if (isCompleted) {
            login(pin)
        } else {
            binding.loginPinScreen.hideError()
        }
    }

    override fun showBiometricsLogin() {
            biometricPrompt.authenticate(biometricInfo)
    }

    override fun shouldShowFingerPrintScreen(): Boolean =
        fingerprintUtils.shouldShowFingerPrintScreen()

    private fun navigateToRegisterFlow() {
        val direction = LoginFragmentDirections.actionLoginFragmentToRegisterActivity()
        findNavController().navigate(direction)
    }

    private fun navigateToDashboard() {
        val direction = if (preferencesManager.containsKey(Constants.USER_BIOMETRICS_TAG)) {
            LoginFragmentDirections.actionLoginFragmentToMainActivity()
        } else {
            LoginFragmentDirections.actionLoginFragmentToBiometricPermissionFragment()
        }
        findNavController().navigate(direction)
    }

    private fun login(pin: String? = null) {
        val phoneNumber =
            preferencesManager.getStringFromPreferences(Constants.USER_PHONE_NUMBER_TAG) ?: ""
        val phonePrefix =
            preferencesManager.getStringFromPreferences(Constants.USER_PHONE_PREFIX_TAG) ?: ""

        val pinToBeSent = pin
            ?: preferencesManager.getEncryptedStringFromPreferences(
                Constants.USER_PIN_TAG,
                Constants.USER_PIN_ALIAS
            )

        try {
            val nPin = pinToBeSent?.toInt() ?: 0
            viewModel.login(phonePrefix, phoneNumber, nPin)
        } catch (e: NumberFormatException) {
        }
    }

}


//todo si el usuario se ha borrado, devolverlo del servidor y lanzar el proceso de registro desde el principio