package com.rookia.android.sejo.ui.register.number

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ValidateNumberFragmentBinding
import kotlinx.android.synthetic.main.validate_number_fragment.*
import javax.inject.Inject

class ValidatePhoneNumberFragment @Inject constructor(private val preferencesManager: PreferencesManager) :
    Fragment(R.layout.validate_number_fragment) {

    private lateinit var binding: ValidateNumberFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ValidateNumberFragmentBinding.bind(view)
        binding.validatePhoneNumberContinueButton.apply {
            setOnClickListener { navigateToSmsValidation() }
//            addTextChangedListener {
//
//            }
        }
    }

    private fun navigateToSmsValidation() {
        findNavController().navigate(ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToValidateSmsFragment())
    }

}