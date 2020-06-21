package com.rookia.android.sejo.ui.register.pincreation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPinCreationStep2Binding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PinScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinCreationStep2Fragment : BaseFragment(R.layout.fragment_pin_creation_step2), PinScreen.PinValidator {

    private lateinit var binding: FragmentPinCreationStep2Binding

    private val viewModel: PinCreationStep2ViewModel by viewModels()

    private var previousCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.apply {
            val safeArgs = PinCreationStep2FragmentArgs.fromBundle(this)
            previousCode = safeArgs.code
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPinCreationStep2Binding.bind(view)
        binding.fragmentPinCreationStep2PinScreen.setHeader(resourcesManager.getString(R.string.fragment_pin_creation_step_2_header))
        binding.fragmentPinCreationStep2PinScreen.setPinValidator(this)
        setToolbar(binding.fragmentPinCreationStep2Toolbar)

        viewModel.pinSentToServer.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it.status){
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        navigateToPersonalInfo()
                    }
                    Result.Status.ERROR -> hideLoading()
                    Result.Status.LOADING -> showLoading()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_pin_menu, menu)
        menu.findItem(R.id.create_pin_menu_item)?.let {
            it.isEnabled = binding.fragmentPinCreationStep2PinScreen.isPinSet()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.create_pin_menu_item -> {
                with(binding.fragmentPinCreationStep2PinScreen) {
                previousCode?.let {
                    if (viewModel.validatePin(it, getPin())) {
                        viewModel.sendPinInfo(getPin())
                    } else {
                        showError()
                    }
                } ?: showError()
            }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onPinChanged(pin: String, isCompleted: Boolean) {
        activity?.invalidateOptionsMenu()
    }

    private fun navigateToPersonalInfo() {
        val direction = PinCreationStep2FragmentDirections.actionPinCreationStep2FragmentToPersonalInfoFragment()
        findNavController().navigate(direction)
    }

    override fun needToHideNavigationBar(): Boolean = true

}
