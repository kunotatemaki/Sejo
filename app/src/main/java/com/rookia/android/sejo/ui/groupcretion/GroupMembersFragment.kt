package com.rookia.android.sejo.ui.groupcretion

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.framework.utils.PermissionManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupMembersBinding
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.ui.common.BaseFragment


class GroupMembersFragment constructor(
    private val viewModelFactory: ViewModelFactory,
    private val permissionManager: PermissionManager
) :
    BaseFragment(R.layout.fragment_group_members), GroupMembersAdapter.GroupMemberListed,
    GroupMembersAddedAdapter.GroupMemberAddedList {

    companion object {
        private const val CONTACTS_PERMISSION_CODE = 1234
    }



    private lateinit var binding: FragmentGroupMembersBinding
    private lateinit var viewModel: GroupMembersViewModel
    private val contactsAdapter = GroupMembersAdapter(this)
    private val contactsAddedAdapter = GroupMembersAddedAdapter(this)

    override fun needToShowBackArrow(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupMembersBinding.bind(view)
        viewModel = injectViewModel(viewModelFactory)
        if (permissionManager.isPermissionGranted(this, Manifest.permission.READ_CONTACTS).not()) {
            binding.fragmentGroupMembersNoContactsContainer.visible()
            binding.fragmentGroupMembersListContainer.gone()
        } else {
            loadContacts()
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
        viewModel.phoneContactsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        contactsAdapter.setPhoneContacts(it.data)
                    }
                    Result.Status.ERROR -> hideLoading()
                    Result.Status.LOADING -> showLoading()
                }
            }
        })

        binding.fragmentGroupMembersList.adapter = contactsAdapter
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentGroupMembersAddedList.adapter = contactsAddedAdapter
        binding.fragmentGroupMembersAddedList.layoutManager = horizontalLayoutManager
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

    private fun loadContacts() {
        permissionManager.requestPermissions(
            fragment = this,
            callbackAllPermissionsGranted = ::permissionGranted,
            permissions = listOf(Manifest.permission.READ_CONTACTS),
            code = CONTACTS_PERMISSION_CODE,
            showRationaleMessageIfNeeded = false

        )
    }

    private fun permissionGranted() {
        binding.fragmentGroupMembersNoContactsContainer.gone()
        binding.fragmentGroupMembersListContainer.visible()
        viewModel.loadPhoneContacts()
    }

    override fun onPhoneContactMember(contact: PhoneContact) {
        contactsAddedAdapter.addPhoneContact(contact)
        binding.fragmentGroupMembersAddedList.smoothScrollToPosition(contactsAddedAdapter.itemCount)
    }

    override fun onPhoneContactMemberAdded(view: View, contact: PhoneContact, position: Int) {

        val anim: Animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.collapse
        )
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.gone()
                contactsAddedAdapter.removePhoneContact(contact, position)
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
//        anim.duration = 300
        view.startAnimation(anim)
//
//        Handler ().postDelayed({
//            contactsAddedAdapter.removePhoneContact(contact, position)
//        }, anim.duration)
    }

}
