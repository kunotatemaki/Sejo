package com.rookia.android.sejo.ui.register.passwordcreation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPasswordCreationStep1Binding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PasswordScreen
import javax.inject.Inject


class PasswordCreationStep1Fragment @Inject constructor(
    private val resourcesManager: ResourcesManager
) : BaseFragment(R.layout.fragment_password_creation_step1),
    PasswordScreen.PasswordValidator {

    private lateinit var binding: FragmentPasswordCreationStep1Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordCreationStep1Binding.bind(view)
        binding.fragmentPasswordCreationPasswordScreen.apply {
            setPasswordValidator(this@PasswordCreationStep1Fragment)
            setHeader(resourcesManager.getString(R.string.fragment_password_creation_step_1_header))
            setSubHeader(resourcesManager.getString(R.string.fragment_password_creation_step_1_subheader))
        }

    }

    override fun checkPassword(password: String) {
        val direction =
            PasswordCreationStep1FragmentDirections.actionPasswordCreationStep1FragmentToPasswordCreationStep2Fragment(
                password
            )
        findNavController().navigate(direction)
    }

}
