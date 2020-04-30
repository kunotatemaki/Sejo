package com.rookia.android.sejo.ui.register.number

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentValidateNumberBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.register.RegisterActivity
import com.rookia.android.sejo.ui.views.PhoneNumberView
import javax.inject.Inject

class ValidatePhoneNumberFragment @Inject constructor(
    private val resourcesManager: ResourcesManager,
    private val preferencesManager: PreferencesManager
) : BaseFragment(R.layout.fragment_validate_number) {

    private lateinit var binding: FragmentValidateNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val validatedPin =
            preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_PIN_SENT_TAG)
        val validatedPhone =
            preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_VALIDATED_PHONE_TAG)
        if (validatedPin && validatedPhone) {
            navigateToPersonalInfo()
        } else if (validatedPhone) {
            navigateToPinCreation()
        }
    }

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
        (activity as RegisterActivity).hideKeyboard()
        findNavController().navigate(
            ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToValidateSmsFragment(
                phonePrefix,
                phoneNumber.replace("\\s+".toRegex(), "")
            )
        )
    }

    private fun navigateToPinCreation() {
        (activity as RegisterActivity).hideKeyboard()
        val direction =
            ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToPinCreationStep1Fragment()
        findNavController().navigate(direction)
    }

    private fun navigateToPersonalInfo() {
        (activity as RegisterActivity).hideKeyboard()
        val direction =
            ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToPersonalInfoFragment()
        findNavController().navigate(direction)
    }

}