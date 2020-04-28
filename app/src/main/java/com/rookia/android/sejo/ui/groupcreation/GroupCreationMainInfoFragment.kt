package com.rookia.android.sejo.ui.groupcreation

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupCreationMainInfoBinding
import com.rookia.android.sejo.ui.common.BaseActivity
import com.rookia.android.sejo.ui.common.BaseFragment


class GroupCreationMainInfoFragment constructor(private val resourcesManager: ResourcesManager) :
    BaseFragment(R.layout.fragment_group_creation_main_info) {

    lateinit var binding: FragmentGroupCreationMainInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupCreationMainInfoBinding.bind(view).also {
            it.fragmentGroupCreationMainInfoButton.setOnClickListener {
                navigateToMembersScreen()
            }
            it.fragmentGroupCreationMainInfoAdmins.addTextChangedListener {
                checkButton()
            }
            it.fragmentGroupCreationMainInfoName.addTextChangedListener {
                checkButton()
            }
        }
        setToolbar(binding.fragmentGroupCreationMainInfoToolbar, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? BaseActivity)?.hideKeyboard()
    }

    private fun navigateToMembersScreen() {
        (activity as? BaseActivity)?.hideKeyboard()
        var adminsOk = true
        var numberOfAdmins = 0
        try {
            numberOfAdmins = binding.fragmentGroupCreationMainInfoAdmins.text.toString().toInt()
            if (numberOfAdmins <= 1) {
                adminsOk = false
            }
        } catch (e: NumberFormatException) {
            adminsOk = false
        }
        val fee = try {
            binding.fragmentGroupCreationMainInfoFee.text.toString().toInt()
        } catch (e: NumberFormatException) {
            0
        }
        if (adminsOk) {
            val direction =
                GroupCreationMainInfoFragmentDirections.actionGroupCreationMainInfoFragmentToGroupCreationMembersFragment(
                    binding.fragmentGroupCreationMainInfoName.text.toString(), numberOfAdmins, fee
                )
            findNavController().navigate(direction)
        } else {
            binding.fragmentGroupCreationMainInfoAdminsContainer.error =
                resourcesManager.getString(R.string.fragment_group_creation_main_admins_error)
        }

    }

    private fun checkButton() {
        var enabled = true
        if (binding.fragmentGroupCreationMainInfoAdmins.text.isNullOrBlank()) {
            enabled = false
        }
        if (binding.fragmentGroupCreationMainInfoName.text.isNullOrBlank()) {
            enabled = false
        }
        binding.fragmentGroupCreationMainInfoButton.isEnabled = enabled
        binding.fragmentGroupCreationMainInfoAdminsContainer.error = null
    }
}
