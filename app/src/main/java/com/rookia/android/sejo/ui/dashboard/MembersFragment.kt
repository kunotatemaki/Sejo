package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.login.LoginStatus

class MembersFragment constructor(
    private val viewModelFactory: ViewModelFactory,
    loginStatus: LoginStatus
) : BaseFragment(R.layout.members_fragment, loginStatus) {

    private lateinit var viewModel: MembersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)

    }

}