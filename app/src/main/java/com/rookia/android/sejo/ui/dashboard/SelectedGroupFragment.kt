package com.rookia.android.sejo.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentSelectedGroupBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedGroupFragment : BaseFragment(R.layout.fragment_selected_group) {

    private val viewModel: SelectedGroupViewModel by viewModels()

    private lateinit var binding: FragmentSelectedGroupBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideKeyboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectedGroupBinding.bind(view)

        setToolbar(binding.fragmentSelectedGroupToolbar)

        binding.fragmentSelectedGroupNoGroupButton.setOnClickListener {
            navigateToGroups()
        }

        viewModel.group.observe(viewLifecycleOwner, Observer {
            it?.let {
                hideNoGroup()
                binding.group = it
                setTitle(it.name)
            }
        })

    }

    override fun needToHideNavigationBar(): Boolean = false

    private fun navigateToGroups() {
        val direction = SelectedGroupFragmentDirections.actionGlobalGroupsFragment()
        findNavController().navigate(direction)
    }

    private fun hideNoGroup() {
        (activity as? MainActivity)?.enableSelectedGroupBottomIcons()
        binding.hideNoGroup = true
    }


}