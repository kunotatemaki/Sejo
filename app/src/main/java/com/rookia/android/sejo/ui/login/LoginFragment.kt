package com.rookia.android.sejo.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.LoginFragmentBinding
import com.rookia.android.sejo.framework.utils.FingerprintUtils
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PinScreen
import timber.log.Timber
import java.util.concurrent.Executor
import javax.inject.Inject


class LoginFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val fingerprintUtils: FingerprintUtils,
    private val biometricInfo: BiometricPrompt.PromptInfo,
    private val preferencesManager: PreferencesManager
) : BaseFragment(R.layout.login_fragment), PinScreen.BiometricHelper,
    PinScreen.PinValidator {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val pinSet = preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_PIN_SENT_TAG)
        val phoneValidated = preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_VALIDATED_PHONE_TAG)
        if(pinSet.not() || phoneValidated.not()){
            navigateToRegisterFlow()
            activity?.finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        binding.loginPinScreen.setBiometricHelper(this)
        binding.loginPinScreen.setPinValidator(this)
        viewModel = injectViewModel(viewModelFactory)

        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
//                    val phoneNumber = SharedPreferencesUtils.get(Constants.PREF_TAGS.SIGN_IN_PROCESS.VALIDATED_PHONE_NUMBER_TAG)
//                    val phonePrefix = SharedPreferencesUtils.get(Constants.PREF_TAGS.SIGN_IN_PROCESS.VALIDATED_PHONE_PREFIX_TAG, Constants.PHONE_NUMBER.SPANISH_PHONE_NUMBER_PREFIX)
//                    val pwdBytes = storageHelper.getData(Constants.PREF_TAGS.SIGN_IN_PROCESS.PASSWORD_TAG)
//                    val pwd = String(pwdBytes)
//                    (presenter as LoginFragmentPresenter).login(phonePrefix, phoneNumber, pwd)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })
    }

    override fun onPinChanged(pin: String, isCompleted: Boolean) {
        Timber.d("")
    }

    override fun showBiometricsLogin() {
        biometricPrompt.authenticate(biometricInfo)
    }

    override fun shouldShowFingerPrintScreen(): Boolean = fingerprintUtils.shouldShowFingerPrintScreen()

    private fun navigateToRegisterFlow() {
        val direction = LoginFragmentDirections.actionLoginFragmentToRegisterActivity()
        findNavController().navigate(direction)
    }



}