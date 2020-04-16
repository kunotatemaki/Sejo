package com.rookia.android.sejo.ui.register.pincreation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPinCreationStep1Binding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.views.PinScreen
import javax.inject.Inject


class PinCreationStep1Fragment @Inject constructor(
    private val resourcesManager: ResourcesManager
) : BaseFragment(R.layout.fragment_pin_creation_step1),
    PinScreen.PinValidator {

    private lateinit var binding: FragmentPinCreationStep1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPinCreationStep1Binding.bind(view)
        binding.fragmentPinCreationPinScreen.apply {
            setPinValidator(this@PinCreationStep1Fragment)
            setHeader(resourcesManager.getString(R.string.fragment_pin_creation_step_1_header))
            setSubHeader(resourcesManager.getString(R.string.fragment_pin_creation_step_1_subheader))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_pin_menu, menu)
        menu.findItem(R.id.create_pin_menu_item)?.let {
            it.isEnabled = binding.fragmentPinCreationPinScreen.isPinSet()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
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
            PinCreationStep1FragmentDirections.actionPasswordCreationStep1FragmentToPasswordCreationStep2Fragment(
                binding.fragmentPinCreationPinScreen.getPin()
            )
        findNavController().navigate(direction)
    }

}
