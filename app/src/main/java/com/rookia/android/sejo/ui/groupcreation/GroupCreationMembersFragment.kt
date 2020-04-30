package com.rookia.android.sejo.ui.groupcreation

import android.Manifest
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.framework.utils.PermissionManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.androidutils.utils.DeviceUtils
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGroupCreationMembersBinding
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.ui.common.BaseFragment
import timber.log.Timber
import java.util.*


class GroupCreationMembersFragment constructor(
    private val viewModelFactory: ViewModelFactory,
    private val permissionManager: PermissionManager,
    private val resourcesManager: ResourcesManager,
    private val deviceUtils: DeviceUtils
) :
    BaseFragment(R.layout.fragment_group_creation_members),
    GroupCreationMembersAdapter.GroupMemberListed,
    GroupCreationMembersAddedAdapter.GroupMemberAddedList {

    companion object {
        private const val CONTACTS_PERMISSION_CODE = 1234
        private const val CONTACTS_ADDED = "CONTACTS_ADDED"
    }

    private lateinit var binding: FragmentGroupCreationMembersBinding
    private lateinit var viewModel: GroupCreationMembersViewModel
    private val contactsAdapter = GroupCreationMembersAdapter(this)
    private val contactsAddedAdapter = GroupCreationMembersAddedAdapter(this)
    private var searchMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupCreationMembersBinding.bind(view)
        setToolbar(binding.fragmentGroupCreationMembersToolbar, true)
        binding.fragmentGroupCreationMembersToolbar.setOnClickListener {
            if (searchMenuItem?.isActionViewExpanded == false) {
                searchMenuItem?.expandActionView()
            }
        }
        viewModel = injectViewModel(viewModelFactory)
        if (permissionManager.isPermissionGranted(this, Manifest.permission.READ_CONTACTS).not()) {
            binding.fragmentGroupCreationMembersNoContactsContainer.visible()
            binding.fragmentGroupCreationMembersListContainer.gone()
            binding.fragmentGroupCreationMembersContinueButton.gone()
        } else {
            loadContacts()
            binding.fragmentGroupCreationMembersContinueButton.show()
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

        binding.fragmentGroupCreationMembersContinueButton.setOnClickListener {
            onFabClicked()
        }

        binding.fragmentGroupCreationMembersList.adapter = contactsAdapter
        binding.fragmentGroupCreationMembersAddedList.adapter = contactsAddedAdapter

        savedInstanceState?.let {
            if (it.containsKey(CONTACTS_ADDED)) {
                contactsAddedAdapter.addListOfContacts(
                    it.getParcelableArrayList<PhoneContact>(
                        CONTACTS_ADDED
                    )?.toList()
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_group_members, menu)
        menu.findItem(R.id.action_search)?.let {
            searchMenuItem = it
            it.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    val strokeColorFrom = resourcesManager.getColor(R.color.toolbar_border_search)
                    val strokeColorTo = resourcesManager.getColor(R.color.toolbar_border)
                    animateSearchBoxStroke(strokeColorFrom, strokeColorTo)
                    return true
                }

                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    val strokeColorFrom = resourcesManager.getColor(R.color.toolbar_border)
                    val strokeColorTo = resourcesManager.getColor(R.color.toolbar_border_search)
                    animateSearchBoxStroke(strokeColorFrom, strokeColorTo)
                    return true
                }

            })

            closeSearchViewWithoutAnimation()
            (searchMenuItem?.actionView as? SearchView)?.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean = true

                override fun onQueryTextChange(text: String?): Boolean {
                    Timber.d("")
                    viewModel.query(text)
                    return true
                }

            })
        }
    }

    private fun closeSearchViewWithoutAnimation() {
        val toolbarBackground =
            binding.fragmentGroupCreationMembersToolbar.background as? GradientDrawable
        val strokeSize = deviceUtils.getPxFromDp(2f).toInt()
        val strokeColor = resourcesManager.getColor(R.color.toolbar_border)
        toolbarBackground?.setStroke(strokeSize, strokeColor)
    }

    private fun animateSearchBoxStroke(strokeColorFrom: Int, strokeColorTo: Int) {
        val toolbarBackground =
            binding.fragmentGroupCreationMembersToolbar.background as? GradientDrawable
        val strokeColorAnimation =
            ValueAnimator.ofObject(ArgbEvaluator(), strokeColorFrom, strokeColorTo)
        strokeColorAnimation.duration = 200
        val strokeSize = deviceUtils.getPxFromDp(2f).toInt()
        strokeColorAnimation.addUpdateListener { animator ->
            val color = animator.animatedValue as Int
            toolbarBackground?.setStroke(strokeSize, color)
        }
        strokeColorAnimation.start()
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
        outState.putParcelableArrayList(
            CONTACTS_ADDED,
            contactsAddedAdapter.getContactsAdded() as? ArrayList<out Parcelable>
        )
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
        binding.fragmentGroupCreationMembersContinueButton.show()
        viewModel.loadPhoneContacts()
    }

    private fun onFabClicked() {
        if (contactsAddedAdapter.getContactsAdded().isEmpty()) {
            Snackbar.make(
                requireView(),
                resourcesManager.getString(R.string.fragment_group_creation_members_add_members_error),
                Snackbar.LENGTH_LONG
            ).show()
        }
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
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.gone()
                contactsAddedAdapter.removePhoneContact(contact, position)
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        view.startAnimation(anim)
    }

}
