package com.rookia.android.sejo.ui.register.pincreation

import android.os.Bundle
import android.view.View
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPinCreationStep2Binding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PinScreen
import javax.inject.Inject

class PinCreationStep2Fragment @Inject constructor(
    private val resourcesManager: ResourcesManager,
    private val viewModelFactory: ViewModelFactory
) : BaseFragment(R.layout.fragment_pin_creation_step2), PinScreen.PinValidator {

    private lateinit var binding: FragmentPinCreationStep2Binding

    private lateinit var viewModel: PinCreationStep2ViewModel

    private var previousCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val safeArgs = PinCreationStep2FragmentArgs.fromBundle(this)
            previousCode = safeArgs.code
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPinCreationStep2Binding.bind(view)
        binding.fragmentPinCreationPinScreen.setHeader(resourcesManager.getString(R.string.fragment_pin_creation_step_2_header))
        binding.fragmentPinCreationPinScreen.setPinValidator(this)
        viewModel = injectViewModel(viewModelFactory)

    }

    override fun onPinChanged(pin: String, isCompleted: Boolean) {
        previousCode?.let {
            if (viewModel.validatePin(it, pin)) {
                viewModel.sendPinInfo()
            } else {
                binding.fragmentPinCreationPinScreen.showError()
            }
        } ?: binding.fragmentPinCreationPinScreen.showError()
    }


}
