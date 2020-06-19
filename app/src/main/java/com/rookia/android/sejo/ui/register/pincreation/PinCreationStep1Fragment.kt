package com.rookia.android.sejo.ui.register.pincreation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPinCreationStep1Binding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PinScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PinCreationStep1Fragment : BaseFragment(R.layout.fragment_pin_creation_step1),
    PinScreen.PinValidator {

    private lateinit var binding: FragmentPinCreationStep1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPinCreationStep1Binding.bind(view)
        binding.fragmentPinCreationStep1PinScreen.apply {
            setPinValidator(this@PinCreationStep1Fragment)
            setHeader(resourcesManager.getString(R.string.fragment_pin_creation_step_1_header))
        }
        setToolbar(
            binding.fragmentPinCreationStep1Toolbar,
            false,
            resourcesManager.getString(R.string.fragment_pin_creation_toolbar_title)
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_pin_menu, menu)
        menu.findItem(R.id.create_pin_menu_item)?.let {
            it.isEnabled = binding.fragmentPinCreationStep1PinScreen.isPinSet()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.create_pin_menu_item -> {
                navigateToPinConfirmationScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onPinChanged(pin: String, isCompleted: Boolean) {
        activity?.invalidateOptionsMenu()
    }

    private fun navigateToPinConfirmationScreen() {
        val direction =
            PinCreationStep1FragmentDirections.actionPinCreationStep1FragmentToPinCreationStep2Fragment(
                binding.fragmentPinCreationStep1PinScreen.getPin()
            )
        findNavController().navigate(direction)
    }

    override fun needTohideNavigationBar(): Boolean = true

}
