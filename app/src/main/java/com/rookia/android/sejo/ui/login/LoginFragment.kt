package com.rookia.android.sejo.ui.login

import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.Constants.USE_FINGERPRINT_TAG
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.LoginFragmentBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PasswordScreen
import com.rookia.android.sejo.utils.FingerprintUtils
import timber.log.Timber
import java.util.concurrent.Executor
import javax.inject.Inject


class LoginFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val fingerprintUtils: FingerprintUtils,
    private val preferencesManager: PreferencesManager,
    private val biometricInfo: BiometricPrompt.PromptInfo
) : BaseFragment(R.layout.login_fragment), PasswordScreen.BiometricHelper,
    PasswordScreen.PasswordValidator {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        binding.loginPasswordScreen.setBiometricHelper(this)
        binding.loginPasswordScreen.setPasswordValidator(this)
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

    override fun checkPassword(password: String) {
        Timber.d("")
    }

    override fun showBiometricsLogin(forceIfAvailable: Boolean) {
//        if (forceIfAvailable) {
//            hideBiometrics = false
//        }
//        if (hideBiometrics.not() && fingerprintUtils.shouldShowLoginWithFingerprint()) {
            biometricPrompt.authenticate(biometricInfo)
//        }
    }

    override fun isFingerPrintSupported(): Boolean =
        fingerprintUtils.isFingerprintSupported()

    override fun isFingerprintSetByTheUser(): Boolean =true
//        preferencesManager.getBooleanFromPreferences(USE_FINGERPRINT_TAG)



}