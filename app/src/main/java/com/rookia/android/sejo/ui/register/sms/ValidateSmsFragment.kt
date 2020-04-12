package com.rookia.android.sejo.ui.register.sms

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ValidateSmsFragmentBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import timber.log.Timber
import javax.inject.Inject


class ValidateSmsFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : BaseFragment(R.layout.validate_sms_fragment) {

    private lateinit var binding: ValidateSmsFragmentBinding
    private lateinit var viewModel: ValidateSmsViewModel
    private lateinit var phoneNumber: String
    private lateinit var phonePrefix: String
    private val filter = IntentFilter().apply {
        addAction("com.google.android.gms.auth.api.phone.SMS_RETRIEVED")
    }

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
        binding = ValidateSmsFragmentBinding.bind(view)
        viewModel = injectViewModel(viewModelFactory)

        if(::phonePrefix.isInitialized && ::phoneNumber.isInitialized) {
            viewModel.requestSms(phonePrefix, phoneNumber).observe(viewLifecycleOwner, Observer {
                it?.let {
                    when(it.status){
                        Result.Status.LOADING -> showLoading()
                        Result.Status.SUCCESS, Result.Status.ERROR -> hideLoading()
                    }
                }
            })
        }

        //todo quitar el helper cuando sepa el código de producción
        val helper = AppSignatureHelper(requireContext())
        val id = helper.appSignatures

        viewModel.receiver.getCode().observe(this.viewLifecycleOwner, Observer {
            it?.let { code ->
                binding.fragmentValidateSmsView.text = code
            }
        })

        context?.registerReceiver(viewModel.receiver, filter)
        startListeningForSms()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.unregisterReceiver(viewModel.receiver)
    }


    private fun startListeningForSms() {
        context?.let {
            SmsRetriever
                .getClient(it)
                .startSmsRetriever()
        }
    }


}