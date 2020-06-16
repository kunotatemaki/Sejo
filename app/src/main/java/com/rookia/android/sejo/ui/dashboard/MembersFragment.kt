package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import com.rookia.android.sejo.R
import com.rookia.android.sejo.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MembersFragment : BaseFragment(R.layout.members_fragment) {

    private lateinit var viewModel: MembersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}