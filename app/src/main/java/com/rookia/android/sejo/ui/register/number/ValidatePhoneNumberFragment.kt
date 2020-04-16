package com.rookia.android.sejo.ui.register.number

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ValidateNumberFragmentBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.register.RegisterActivity
import com.rookia.android.sejo.ui.views.PhoneNumberView
import javax.inject.Inject

class ValidatePhoneNumberFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val resourcesManager: ResourcesManager,
    private val preferencesManager: PreferencesManager
) : BaseFragment(R.layout.validate_number_fragment) {

    private lateinit var binding: ValidateNumberFragmentBinding
    private lateinit var viewModel: ValidatePhoneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val validatedPhone = preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_VALIDATED_PHONE_TAG)
        if(validatedPhone){
            navigateToPinCreation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ValidateNumberFragmentBinding.bind(view)
        viewModel = injectViewModel(viewModelFactory)
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
        (activity as RegisterActivity).hideKeyboard()
        findNavController().navigate(
            ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToValidateSmsFragment(
                phonePrefix,
                phoneNumber.replace("\\s+".toRegex(),"")
            )
        )
    }

    private fun navigateToPinCreation() {
        (activity as RegisterActivity).hideKeyboard()
        val direction = ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToPinCreationStep1Fragment()
        findNavController().navigate(direction)
    }

}