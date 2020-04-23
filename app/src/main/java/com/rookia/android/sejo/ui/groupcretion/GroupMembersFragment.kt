package com.rookia.android.sejo.ui.groupcretion

import android.Manifest
import android.os.Bundle
import android.view.View
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.framework.utils.PermissionManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupMembersBinding
import com.rookia.android.sejo.ui.common.BaseFragment


class GroupMembersFragment constructor(private val permissionManager: PermissionManager) :
    BaseFragment(R.layout.fragment_group_members) {

    companion object {
        private const val CONTACTS_PERMISSION_CODE = 1234
    }

    private lateinit var binding: FragmentGroupMembersBinding

    override fun needToShowBackArrow(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupMembersBinding.bind(view)
        if (permissionManager.isPermissionGranted(this, Manifest.permission.READ_CONTACTS).not()) {
            binding.fragmentGroupMembersContainer.visible()
        }
        binding.fragmentGroupMembersPermissionsButton.setOnClickListener {
            permissionManager.requestPermissions(
                fragment = this,
                callbackAllPermissionsGranted = ::permissionGranted,
                permissions = listOf(Manifest.permission.READ_CONTACTS),
                code = CONTACTS_PERMISSION_CODE,
                showRationaleMessageIfNeeded = false

            )
        }
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

    private fun permissionGranted() {
        binding.fragmentGroupMembersContainer.gone()
    }

}
