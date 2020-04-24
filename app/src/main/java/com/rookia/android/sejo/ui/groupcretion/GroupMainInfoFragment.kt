package com.rookia.android.sejo.ui.groupcretion

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupMainInfoBinding
import com.rookia.android.sejo.ui.common.BaseActivity
import com.rookia.android.sejo.ui.common.BaseFragment


class GroupMainInfoFragment constructor(private val resourcesManager: ResourcesManager): BaseFragment(R.layout.fragment_group_main_info) {

    lateinit var binding: FragmentGroupMainInfoBinding

    override fun needToShowBackArrow(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupMainInfoBinding.bind(view). also {
            it.fragmentGroupMainInfoButton.setOnClickListener {
                navigateToMembersScreen()
            }
            it.fragmentGroupMainInfoAdmins.addTextChangedListener {
                checkButton()
            }
            it.fragmentGroupMainInfoName.addTextChangedListener {
                checkButton()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? BaseActivity)?.hideKeyboard()
    }

    private fun navigateToMembersScreen() {
        var adminsOk = true
        var numberOfAdmins = 0
        try {
            numberOfAdmins = binding.fragmentGroupMainInfoAdmins.text.toString().toInt()
            if(numberOfAdmins <= 0){
                adminsOk = false
            }
        } catch (e: NumberFormatException){
            adminsOk = false
        }
        if(adminsOk){
            val direction = GroupMainInfoFragmentDirections.actionGroupMainInfoFragmentToGroupMembersFragment(
                binding.fragmentGroupMainInfoName.text.toString(), numberOfAdmins
            )
            findNavController().navigate(direction)
        } else {
            binding.fragmentGroupMainInfoAdminsContainer.error = resourcesManager.getString(R.string.fragment_group_main_admins_error)
        }

    }

    private fun checkButton() {
        var enabled = true
        if(binding.fragmentGroupMainInfoAdmins.text.isNullOrBlank()){
            enabled = false
        }
        if(binding.fragmentGroupMainInfoName.text.isNullOrBlank()){
            enabled = false
        }
        binding.fragmentGroupMainInfoButton.isEnabled = enabled
        binding.fragmentGroupMainInfoAdminsContainer.error = null
    }
}
