package com.rookia.android.sejo.ui.login

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
import com.rookia.android.sejo.domain.network.LoginResponseCodes
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
    private val resourcesManager: ResourcesManager,
    loginStatus: LoginStatus
) : BaseFragment(R.layout.fragment_login, loginStatus), PinScreen.BiometricHelper,
    PinScreen.PinValidator {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt


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
                        if (it.data?.result == LoginResponseCodes.NO_USER.code) {
                            preferencesManager.setBooleanIntoPreferences(
                                Constants.NAVIGATION_PERSONAL_INFO_TAG,
                                false
                            )
                            preferencesManager.setBooleanIntoPreferences(
                                Constants.NAVIGATION_PIN_SENT_TAG,
                                false
                            )
                            preferencesManager.setBooleanIntoPreferences(
                                Constants.NAVIGATION_VALIDATED_PHONE_TAG,
                                false
                            )
                        } else {
                            when {
                                shouldShowBiometricPermission() -> {
                                    navigateToBiometricPermission()
                                }
                                else -> {
                                    findNavController().popBackStack()
                                }
                            }
                        }
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

    private fun shouldShowBiometricPermission(): Boolean =
        fingerprintUtils.isFingerprintSupported() && preferencesManager.containsKey(Constants.USER_BIOMETRICS_TAG)
            .not()

    private fun navigateToBiometricPermission() {
        viewModel.loginResult.removeObservers(viewLifecycleOwner)
        val direction = LoginFragmentDirections.actionLoginFragmentToBiometricPermissionFragment()
        findNavController().navigate(direction)
    }


    private fun login(pin: String? = null) {
        val pinToBeSent = pin
            ?: preferencesManager.getEncryptedStringFromPreferences(
                Constants.USER_PIN_TAG,
                Constants.USER_PIN_ALIAS
            )

        try {
            val nPin = pinToBeSent?.toInt() ?: 0
            viewModel.login(nPin)
        } catch (e: NumberFormatException) {
        }
    }

}
