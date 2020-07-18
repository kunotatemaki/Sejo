package com.rookia.android.sejo.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentBiometricPermisssionBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BiometricPermissionFragment :
    BaseFragment(R.layout.fragment_biometric_permisssion) {

    private lateinit var binding: FragmentBiometricPermisssionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBiometricPermisssionBinding.bind(view)
        binding.fragmentBiometricPermissionActivateButton.setOnClickListener {
            preferencesManager.setBooleanIntoPreferences(Constants.UserData.BIOMETRICS_TAG, true)
            navigate()
        }
        binding.fragmentBiometricPermissionNotNowButton.setOnClickListener {
            preferencesManager.setBooleanIntoPreferences(Constants.UserData.BIOMETRICS_TAG, false)
            navigate()
        }

    }

    private fun navigate() {
        findNavController().popBackStack(R.id.loginFragment, true)
    }

    override fun doOnBackPressed() {
    }

    override fun needToHideNavigationBar(): Boolean = true
}
