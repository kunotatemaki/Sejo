package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.MembersFragmentBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejocore.domain.local.Group
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MembersFragment : BaseFragment(R.layout.members_fragment),
    MembersAdapter.GroupMemberInAdapter {

    private val viewModel: MembersViewModel by viewModels()
    private lateinit var binding: MembersFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MembersFragmentBinding.bind(view)

        setToolbar(binding.fragmentMembersToolbar)

        viewModel.group.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.fragmentMembersList.adapter = MembersAdapter(it.members, this)
            }
        })
    }

    override fun needToHideNavigationBar(): Boolean = false

    override fun onGroupMemberClicked(member: Group.GroupContact) {
        Toast.makeText(context, "coming soon", Toast.LENGTH_SHORT).show()
    }

}