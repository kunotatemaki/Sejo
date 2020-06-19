package com.rookia.android.sejo.ui.register.number

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentValidateNumberBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.main.MainActivity
import com.rookia.android.sejo.ui.views.PhoneNumberView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ValidatePhoneNumberFragment : BaseFragment(R.layout.fragment_validate_number) {

    private lateinit var binding: FragmentValidateNumberBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentValidateNumberBinding.bind(view)
        binding.validatePhoneNumberContinueButton.setOnClickListener {
            navigateToSmsValidation(
                binding.validatePhoneNumberPhoneNumber.phonePrefix,
                binding.validatePhoneNumberPhoneNumber.phoneNumber
            )
        }
        binding.validatePhoneNumberPhoneNumber.isValidPhone.observe(
            this.viewLifecycleOwner,
            Observer {
                it?.let {
                    when (it) {
                        PhoneNumberView.PhoneNumberFormat.OK -> {
                            binding.buttonEnabled = true
                            binding.validatePhoneNumberPhoneNumber.hideError()
                        }
                        PhoneNumberView.PhoneNumberFormat.INCOMPLETE -> {
                            binding.buttonEnabled = false
                            binding.validatePhoneNumberPhoneNumber.hideError()
                        }
                        PhoneNumberView.PhoneNumberFormat.WRONG -> {
                            binding.buttonEnabled = false
                            binding.validatePhoneNumberPhoneNumber.showError(
                                resourcesManager.getString(
                                    R.string.component_phone_number_error
                                )
                            )
                        }
                    }
                }
            })


    }

    private fun navigateToSmsValidation(phonePrefix: String, phoneNumber: String) {
        (activity as MainActivity).hideKeyboard()
        findNavController().navigate(
            ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToValidateSmsFragment(
                phonePrefix,
                phoneNumber.replace("\\s+".toRegex(), "")
            )
        )
    }

    override fun doOnBackPressed() {
        activity?.finish()
    }

    override fun needTohideNavigationBar(): Boolean = true

}