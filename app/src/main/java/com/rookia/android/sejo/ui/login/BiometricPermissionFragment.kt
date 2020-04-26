package com.rookia.android.sejo.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentBiometricPermisssionBinding
import com.rookia.android.sejo.ui.common.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class BiometricPermissionFragment constructor(private val preferencesManager: PreferencesManager) :
    BaseFragment(R.layout.fragment_biometric_permisssion) {

    private lateinit var binding: FragmentBiometricPermisssionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBiometricPermisssionBinding.bind(view)
        binding.fragmentBiometricPermissionActivateButton.setOnClickListener {
            preferencesManager.setBooleanIntoPreferences(Constants.USER_BIOMETRICS_TAG, true)
            navigate()
        }
        binding.fragmentBiometricPermissionNotNowButton.setOnClickListener {
            preferencesManager.setBooleanIntoPreferences(Constants.USER_BIOMETRICS_TAG, false)
            navigate()
        }

    }

    private fun navigate() {
        val direction = BiometricPermissionFragmentDirections.actionBiometricPermissionFragmentToMainActivity()
        findNavController().navigate(direction)
    }
}
