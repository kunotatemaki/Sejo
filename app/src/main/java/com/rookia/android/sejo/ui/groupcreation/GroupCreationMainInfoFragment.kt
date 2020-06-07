package com.rookia.android.sejo.ui.groupcreation

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupCreationMainInfoBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import com.rookia.android.sejo.ui.main.MainActivity


class GroupCreationMainInfoFragment constructor(loginStatus: LoginStatus) :
    BaseFragment(R.layout.fragment_group_creation_main_info, loginStatus) {

    lateinit var binding: FragmentGroupCreationMainInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupCreationMainInfoBinding.bind(view).also {
            it.fragmentGroupCreationMainInfoButton.setOnClickListener {
                navigateToMembersScreen()
            }
            it.fragmentGroupCreationMainInfoName.addTextChangedListener {
                checkButton()
            }
        }
        setToolbar(binding.fragmentGroupCreationMainInfoToolbar, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.hideKeyboard()
    }

    private fun navigateToMembersScreen() {
        (activity as? MainActivity)?.hideKeyboard()
        val fee = try {
            binding.fragmentGroupCreationMainInfoFee.text.toString().toInt()
        } catch (e: NumberFormatException) {
            0
        }
        val direction =
                GroupCreationMainInfoFragmentDirections.actionGroupCreationMainInfoFragmentToGroupCreationMembersFragment(
                    binding.fragmentGroupCreationMainInfoName.text.toString(), fee
                )
            findNavController().navigate(direction)

    }

    private fun checkButton() {
        var enabled = true
        if (binding.fragmentGroupCreationMainInfoName.text.isNullOrBlank()) {
            enabled = false
        }
        binding.fragmentGroupCreationMainInfoButton.isEnabled = enabled
    }
}
