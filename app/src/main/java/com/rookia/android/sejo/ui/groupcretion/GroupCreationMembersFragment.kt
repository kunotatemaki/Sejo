package com.rookia.android.sejo.ui.groupcretion

import android.Manifest
import android.os.Bundle
import android.os.Parcelable
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
import com.rookia.android.sejo.databinding.FragmentGroupCreationMembersBinding
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.ui.common.BaseFragment
import java.util.*


class GroupCreationMembersFragment constructor(
    private val viewModelFactory: ViewModelFactory,
    private val permissionManager: PermissionManager
) :
    BaseFragment(R.layout.fragment_group_creation_members), GroupCreationMembersAdapter.GroupMemberListed,
    GroupCreationMembersAddedAdapter.GroupMemberAddedList {

    companion object {
        private const val CONTACTS_PERMISSION_CODE = 1234
        private const val CONTACTS_ADDED = "CONTACTS_ADDED"
    }

    private lateinit var binding: FragmentGroupCreationMembersBinding
    private lateinit var viewModel: GroupCreationMembersViewModel
    private val contactsAdapter = GroupCreationMembersAdapter(this)
    private val contactsAddedAdapter = GroupCreationMembersAddedAdapter(this)

    override fun needToShowBackArrow(): Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupCreationMembersBinding.bind(view)
        viewModel = injectViewModel(viewModelFactory)
        if (permissionManager.isPermissionGranted(this, Manifest.permission.READ_CONTACTS).not()) {
            binding.fragmentGroupCreationMembersNoContactsContainer.visible()
            binding.fragmentGroupCreationMembersListContainer.gone()
        } else {
            loadContacts()
        }
        binding.fragmentGroupCreationMembersPermissionsButton.setOnClickListener {
            loadContacts()
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

        binding.fragmentGroupCreationMembersList.adapter = contactsAdapter
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentGroupCreationMembersAddedList.adapter = contactsAddedAdapter
        binding.fragmentGroupCreationMembersAddedList.layoutManager = horizontalLayoutManager
        savedInstanceState?.let {
            if(it.containsKey(CONTACTS_ADDED)) {
                contactsAddedAdapter.addListOfContacts(it.getParcelableArrayList<PhoneContact>(CONTACTS_ADDED)?.toList())
            }
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(CONTACTS_ADDED, contactsAddedAdapter.getContactsAdded() as? ArrayList<out Parcelable>)
        super.onSaveInstanceState(outState)
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
        binding.fragmentGroupCreationMembersNoContactsContainer.gone()
        binding.fragmentGroupCreationMembersListContainer.visible()
        viewModel.loadPhoneContacts()
    }

    override fun onPhoneContactMember(contact: PhoneContact) {
        contactsAddedAdapter.addPhoneContact(contact)
        binding.fragmentGroupCreationMembersAddedList.smoothScrollToPosition(contactsAddedAdapter.itemCount)
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
