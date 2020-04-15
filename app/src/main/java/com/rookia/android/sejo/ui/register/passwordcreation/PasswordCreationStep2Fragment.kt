package com.rookia.android.sejo.ui.register.passwordcreation

import android.os.Bundle
import android.view.View
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPasswordCreationStep2Binding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PasswordScreen
import javax.inject.Inject

class PasswordCreationStep2Fragment @Inject constructor(
    private val resourcesManager: ResourcesManager,
    private val viewModelFactory: ViewModelFactory
) : BaseFragment(R.layout.fragment_password_creation_step2), PasswordScreen.PasswordValidator {

    private lateinit var binding: FragmentPasswordCreationStep2Binding

    private lateinit var viewModel: PasswordCreationStep2ViewModel

    private var previousCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val safeArgs = PasswordCreationStep2FragmentArgs.fromBundle(this)
            previousCode = safeArgs.code
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordCreationStep2Binding.bind(view)
        binding.fragmentPasswordCreationPasswordScreen.setHeader(resourcesManager.getString(R.string.fragment_password_creation_step_2_header))
        binding.fragmentPasswordCreationPasswordScreen.setPasswordValidator(this)
        viewModel = injectViewModel(viewModelFactory)

    }

    override fun checkPassword(password: String) {
        previousCode?.let {
            if (viewModel.validatePassword(it, password)) {
                viewModel.sendPasswordInfo()
            } else {
                binding.fragmentPasswordCreationPasswordScreen.showError()
            }
        } ?: binding.fragmentPasswordCreationPasswordScreen.showError()
    }


}
