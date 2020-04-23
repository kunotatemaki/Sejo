package com.rookia.android.sejo.ui.groupcretion

import android.os.Bundle
import android.view.View
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupMainInfoBinding
import com.rookia.android.sejo.ui.common.BaseFragment


class GroupMainInfoFragment : BaseFragment(R.layout.fragment_group_main_info) {

    lateinit var binding: FragmentGroupMainInfoBinding

    override fun needToShowBackArrow(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupMainInfoBinding.bind(view)
    }
}
