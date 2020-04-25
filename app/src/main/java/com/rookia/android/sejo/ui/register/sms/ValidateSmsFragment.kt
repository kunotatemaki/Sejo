package com.rookia.android.sejo.ui.register.sms

import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.extensions.invisible
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.Constants.SMS_PIN_LENGTH
import com.rookia.android.sejo.Constants.SMS_RESEND_WAITING_TIME
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentValidateSmsBinding
import com.rookia.android.sejo.ui.common.BaseActivity
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.SmsCodeView
import com.rookia.android.sejo.utils.TextFormatUtils
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ValidateSmsFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val textFormatUtils: TextFormatUtils
) : BaseFragment(R.layout.fragment_validate_sms), SmsCodeView.OnTextChangeListener {

    private lateinit var binding: FragmentValidateSmsBinding
    private lateinit var viewModel: ValidateSmsViewModel
    private lateinit var phoneNumber: String
    private lateinit var phonePrefix: String
    private val filter = IntentFilter().apply {
        addAction("com.google.android.gms.auth.api.phone.SMS_RETRIEVED")
    }

    private val countDownHandler: Handler = Handler()
    private var remainingSeconds: Int = SMS_RESEND_WAITING_TIME

    private val countDownRunnable: Runnable = object : Runnable {
        override fun run() {
            try {
                updateCountDown()
            } finally {
                if (remainingSeconds >= 0) {
                    countDownHandler.postDelayed(this, TimeUnit.SECONDS.toMillis(1))
                } else {
                    remainingSeconds = 0
                    displayResendMessage()
                }
            }
        }
    }

    override fun needToShowBackArrow() : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val safeVarargs = ValidateSmsFragmentArgs.fromBundle(this)
            phoneNumber = safeVarargs.phoneNumber
            phonePrefix = safeVarargs.phonePrefix
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentValidateSmsBinding.bind(view)
        binding.textFormatUtils = textFormatUtils
        binding.fragmentValidateSmsView.setOnTextChangeListener(this)
        binding.fragmentValidateSmsView.setPhoneText(phonePrefix, phoneNumber)
        viewModel = injectViewModel(viewModelFactory)

        requestSmsCode()

        binding.fragmentValidateSmsFooterResend.setOnClickListener {
            requestSmsCode()
        }

        //todo quitar el helper cuando sepa el código de producción
        val helper = AppSignatureHelper(requireContext())
        val id = helper.appSignatures
        id.firstOrNull()?.let {
            Timber.d("El código para los sms es $it")
        }

        viewModel.receiver.getCode().observe(this.viewLifecycleOwner, Observer {
            it?.let { code ->
                binding.fragmentValidateSmsView.text = code
            }
        })

        context?.registerReceiver(viewModel.receiver, filter)
        startListeningForSms()

        viewModel.smsCodeValidationResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        when (it.data?.result) {
                            Constants.SMS_CODE_OK -> {
                                viewModel.storeValidatedPhone(phonePrefix, phoneNumber)
                                binding.fragmentValidateSmsView.hideError()
                                if(it.data?.userExists == false) {
                                    navigateToCreatePin()
                                }else{
                                    viewModel.setPinSet()
                                    navigateToDashboard()
                                }
                            }
                            Constants.SMS_CODE_EXPIRED -> binding.fragmentValidateSmsView.displayExpiredCode()
                            else -> binding.fragmentValidateSmsView.displayWrongCode()
                        }
                    }
                    Result.Status.ERROR -> {
                        hideLoading()
                        binding.fragmentValidateSmsView.displayWrongCode()
                    }
                    Result.Status.LOADING -> showLoading()
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.unregisterReceiver(viewModel.receiver)
    }

    override fun onResume() {
        super.onResume()
        startCountDown()
    }

    override fun onPause() {
        super.onPause()
        stopCountDown()
    }

    private fun startListeningForSms() {
        context?.let {
            SmsRetriever
                .getClient(it)
                .startSmsRetriever()
        }
    }

    fun updateCountDown() {
        binding.remainingTime = remainingSeconds--
    }

    fun displayResendMessage() {
        binding.fragmentValidateSmsFooterWait.invisible()
        binding.fragmentValidateSmsFooterResend.visible()
    }

    fun hideResendMessage() {
        binding.fragmentValidateSmsFooterWait.visible()
        binding.fragmentValidateSmsFooterResend.invisible()
    }

    fun startCountDown() {
        remainingSeconds = SMS_RESEND_WAITING_TIME
        countDownRunnable.run()
    }

    fun stopCountDown() {
        remainingSeconds = 0
        countDownHandler.removeCallbacks(countDownRunnable)
    }

    override fun onText(newText: String) {
        if (newText.length >= SMS_PIN_LENGTH && argumentsInitialized()) {
            viewModel.validateCode(
                phonePrefix,
                phoneNumber,
                textFormatUtils.removeHyphenFromSmsCode(newText)
            )
        } else {
            binding.fragmentValidateSmsView.hideError()
        }
    }

    private fun argumentsInitialized(): Boolean =
        ::phonePrefix.isInitialized && ::phoneNumber.isInitialized

    fun requestSmsCode() {
        if (argumentsInitialized()) {
            hideResendMessage()
            viewModel.requestSms(phonePrefix, phoneNumber).observe(viewLifecycleOwner, Observer {
                it?.let {
                    when (it.status) {
                        Result.Status.LOADING -> showLoading()
                        Result.Status.SUCCESS, Result.Status.ERROR -> {
                            hideLoading()
                            startCountDown()
                        }
                    }
                }
            })
        }
    }

    private fun navigateToDashboard() {
        (activity as? BaseActivity)?.hideKeyboard()
        val direction = ValidateSmsFragmentDirections.actionValidateSmsFragmentToLoginActivity()
        findNavController().navigate(direction)
        activity?.finish()
    }

    private fun navigateToCreatePin(){
        (activity as? BaseActivity)?.hideKeyboard()
        val direction = ValidateSmsFragmentDirections.actionValidateSmsFragmentToPinCreationStep1Fragment()
        findNavController().navigate(direction)
    }


}
