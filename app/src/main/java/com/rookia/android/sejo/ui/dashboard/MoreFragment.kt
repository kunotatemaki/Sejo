package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentMoreBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment(R.layout.fragment_more) {

    private val viewModel: MoreViewModel by viewModels()

    private lateinit var binding: FragmentMoreBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreBinding.bind(view)

        setToolbar(binding.fragmentMoreToolbar)

        binding.elementMoreMyGroups.root.setOnClickListener {
            navigateToMyGroups()
        }

        binding.elementMoreMyGroups.name = resourcesManager.getString(R.string.fragment_more_my_groups)

    }

    override fun needToHideNavigationBar(): Boolean = false

    private fun navigateToMyGroups() {
        val direction = MoreFragmentDirections.actionMoreFragmentToGroupsFragment()
        findNavController().navigate(direction)
    }

}