package com.rookia.android.sejo.ui.register.sms

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ValidateSmsFragmentBinding
import javax.inject.Inject


class ValidateSmsFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment(R.layout.validate_sms_fragment) {

    private lateinit var binding: ValidateSmsFragmentBinding
    private lateinit var viewModel: ValidateSmsViewModel
    private var phoneNumber: String? = null
    private var phonePrefix: String? = null
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
        viewModel.requestSms(phonePrefix, phoneNumber)

        //todo quitar el helper cuando sepa el código de producción
        val helper = AppSignatureHelper(requireContext())
        val id = helper.appSignatures

        viewModel.code.observe(this.viewLifecycleOwner, Observer {
            it?.let{code ->
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