package com.rookia.android.sejo.ui.groupcretion

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.framework.utils.PermissionManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupMembersBinding
import com.rookia.android.sejo.ui.common.BaseFragment


class GroupMembersFragment constructor(
    private val viewModelFactory: ViewModelFactory,
    private val permissionManager: PermissionManager
) :
    BaseFragment(R.layout.fragment_group_members) {

    companion object {
        private const val CONTACTS_PERMISSION_CODE = 1234
    }

    private lateinit var binding: FragmentGroupMembersBinding
    private lateinit var viewModel: GroupMembersViewModel

    override fun needToShowBackArrow(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupMembersBinding.bind(view)
        viewModel = injectViewModel(viewModelFactory)
        if (permissionManager.isPermissionGranted(this, Manifest.permission.READ_CONTACTS).not()) {
            binding.fragmentGroupMembersContainer.visible()
        } else {
            loadContacts()
        }
        binding.fragmentGroupMembersPermissionsButton.setOnClickListener {

        }
        viewModel.phoneContactsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it.status){
                    Result.Status.SUCCESS -> {
                        hideLoading()
                    }
                    Result.Status.ERROR -> hideLoading()
                    Result.Status.LOADING -> showLoading()
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CONTACTS_PERMISSION_CODE -> {
                if (permissionManager.arePermissionsGrantedByTheUser(grantResults)) {
                    permissionGranted()
                }
            }
        }

    }

    fun loadContacts(){
        permissionManager.requestPermissions(
            fragment = this,
            callbackAllPermissionsGranted = ::permissionGranted,
            permissions = listOf(Manifest.permission.READ_CONTACTS),
            code = CONTACTS_PERMISSION_CODE,
            showRationaleMessageIfNeeded = false

        )
    }

    private fun permissionGranted() {
        binding.fragmentGroupMembersContainer.gone()
        viewModel.loadPhoneContacts()
    }

}
