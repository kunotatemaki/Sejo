package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.utils.RecyclerViewAdapterUtils
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupsBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class GroupsFragment : BaseFragment(R.layout.fragment_groups), GroupsAdapter.GroupItemClickable {

    @Inject
    lateinit var recyclerViewAdapterUtils: RecyclerViewAdapterUtils

    private val viewModel: GroupsViewModel by viewModels()
    private lateinit var adapter: GroupsAdapter

    private lateinit var binding: FragmentGroupsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)

        setToolbar(binding.fragmentGroupsToolbar)

        adapter = GroupsAdapter(this)
        binding.fragmentGroupsAddButton.setOnClickListener {
            navigateToGroupCreation()
        }

        recyclerViewAdapterUtils.scrollToTopIfItemsInsertedArePlacedBeforeTheFirstRow(
            adapter,
            binding.fragmentGroupsList
        )

        binding.fragmentGroupsList.adapter = adapter

        viewModel.groups.observe(viewLifecycleOwner, Observer {
            Timber.d("")
            when (it.status) {
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

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGroups()
        (activity as? MainActivity)?.hideKeyboard()
    }

    private fun navigateToGroupCreation() {
        val direction =
            GroupsFragmentDirections.actionGroupsFragmentToGroupCreationActivity()
        findNavController().navigate(direction)
    }

    override fun onGroupClicked(groupId: Long) {
        navigateToSelectedGroup(groupId)
    }

    override fun needToHideNavigationBar(): Boolean = true

    private fun navigateToSelectedGroup(groupId: Long) {

        preferencesManager.setLongIntoPreferences(
            Constants.UserData.LAST_USED_GROUP_TAG,
            groupId
        )

        (activity as? MainActivity)?.apply {
            enableSelectedGroupBottomIcons()
            navigateToFragment(MainActivity.Companion.BottomMenuType.DASHBOARD)
        }

    }

}