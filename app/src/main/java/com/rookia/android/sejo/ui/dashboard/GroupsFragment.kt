package com.rookia.android.sejo.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupsBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GroupsFragment : BaseFragment(R.layout.fragment_groups), GeneralAdapter.GroupItemClickable {


    private val viewModel: GroupsViewModel by viewModels()
    private lateinit var adapter: GeneralAdapter

    private lateinit var binding: FragmentGroupsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideKeyboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)

        adapter = GeneralAdapter(this)
        binding.fragmentGroupsAddButton.setOnClickListener {
            navigateToGroupCreation()
        }

        binding.fragmentGroupsList.adapter = adapter

        viewModel.groups.observe(viewLifecycleOwner, Observer {
            Timber.d("")
            when(it.status){
                Result.Status.SUCCESS -> {
                    hideLoading()
                    adapter.submitList(it.data)
                }
                Result.Status.LOADING -> {
                    showLoading()
                    adapter.submitList(it.data)
                }
                Result.Status.ERROR -> {
                    hideLoading()
                }
            }


        })

        viewModel.group.observe(viewLifecycleOwner, Observer {
            it?.let {
                preferencesManager.setLongIntoPreferences(Constants.LAST_USED_GROUP_TAG, it.groupId)
                binding.fragmentGroupsLastSelectedItem.group = it
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGroups()
    }

    private fun navigateToGroupCreation() {
        val direction =
            GroupsFragmentDirections.actionBlankFragmentToGroupCreationActivity()
        findNavController().navigate(direction)
    }

    override fun onGroupClicked(groupId: Long) {
        viewModel.setSelectedGroup(groupId)
    }

    override fun needTohideNavigationBar(): Boolean = false

}