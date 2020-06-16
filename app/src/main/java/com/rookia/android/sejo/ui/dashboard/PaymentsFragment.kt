package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rookia.android.sejo.R
import com.rookia.android.sejo.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsFragment : BaseFragment(R.layout.payments_fragment) {

    private val viewModel: PaymentsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}