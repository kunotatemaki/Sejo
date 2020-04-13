package com.rookia.android.sejo.ui.register.number

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ValidateNumberFragmentBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PhoneNumberView
import javax.inject.Inject

class ValidatePhoneNumberFragment @Inject constructor(
    private val resourcesManager: ResourcesManager
) : BaseFragment(R.layout.validate_number_fragment) {

    private lateinit var binding: ValidateNumberFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ValidateNumberFragmentBinding.bind(view)
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
                                    R.string.component_phone_nuber_error
                                )
                            )
                        }
                    }
                }
            })
    }

    private fun navigateToSmsValidation(phonePrefix: String, phoneNumber: String) {
        findNavController().navigate(
            ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToValidateSmsFragment(
                phonePrefix,
                phoneNumber.replace("\\s+".toRegex(),"")
            )
        )
    }

}