package com.rookia.android.sejo.ui.register.number

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.R
import kotlinx.android.synthetic.main.validate_number_fragment.*
import javax.inject.Inject

class ValidatePhoneNumberFragment @Inject constructor(private val preferencesManager: PreferencesManager) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.validate_number_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager.setIntIntoPreferences("hola", 3)
        button.setOnClickListener { navigate() }
    }

    private fun navigate() {
        findNavController().navigate(ValidateNumberFragmentDirections.actionValidateNumberFragmentToValidateSmsFragment())
    }

}