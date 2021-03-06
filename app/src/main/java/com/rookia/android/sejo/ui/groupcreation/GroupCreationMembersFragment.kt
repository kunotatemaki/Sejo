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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.framework.utils.PermissionManager
import com.rookia.android.androidutils.utils.DeviceUtils
import com.rookia.android.sejo.R
import com.rookia.android.sejo.data.CacheSanity
import com.rookia.android.sejo.databinding.FragmentGroupCreationMembersBinding
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.*
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GroupCreationMembersFragment : BaseFragment(R.layout.fragment_group_creation_members),
    GroupCreationMembersAdapter.GroupMemberListed,
    GroupCreationMembersAddedAdapter.GroupMemberRemovedList {

    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var deviceUtils: DeviceUtils

    companion object {
        private const val CONTACTS_PERMISSION_CODE = 1234
        private const val CONTACTS_ADDED = "CONTACTS_ADDED"
    }

    private lateinit var binding: FragmentGroupCreationMembersBinding

    private val viewModel: GroupCreationMembersViewModel by viewModels()
    private lateinit var contactsAdapter: GroupCreationMembersAdapter
    private lateinit var contactsAddedAdapter: GroupCreationMembersAddedAdapter
    private var searchMenuItem: MenuItem? = null
    private lateinit var groupName: String
    private var fee: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        arguments?.apply {
            val safeArgs = GroupCreationMembersFragmentArgs.fromBundle(this)
            groupName = safeArgs.groupName
            fee = safeArgs.fee
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentGroupCreationMembersBinding.bind(view)

        setToolbar(binding.fragmentGroupCreationMembersToolbar)

        binding.fragmentGroupCreationMembersToolbar.setOnClickListener {
            if (searchMenuItem?.isActionViewExpanded == false) {
                searchMenuItem?.expandActionView()
            }
        }

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

        viewModel.groupCreationResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        CacheSanity.groupsCacheDirty = true
                        navigateToDashboard()
                    }
                    Result.Status.ERROR -> hideLoading()
                    Result.Status.LOADING -> showLoading()
                }
            }
        })

        binding.fragmentGroupCreationMembersContinueButton.setOnClickListener {
            onCreateGroupButtonClicked()
        }


        contactsAdapter = GroupCreationMembersAdapter(this)
        binding.fragmentGroupCreationMembersList.adapter = contactsAdapter

        contactsAddedAdapter = GroupCreationMembersAddedAdapter(this, resourcesManager)
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

                override fun onQueryTextChange(text: String): Boolean {
                    viewModel.queryChannel.offer(text)
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

    private fun onCreateGroupButtonClicked() {

        //todo confirmar antes de crear grupo
        when {
            contactsAddedAdapter.getContactsAdded().isEmpty() -> {
                Snackbar.make(
                    requireView(),
                    resourcesManager.getString(R.string.fragment_group_creation_members_add_members_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else -> {
                viewModel.createGroup(
                    name = groupName,
                    fee = fee,
                    members = contactsAddedAdapter.getContactsAdded()
                )
            }
        }

    }

    override fun onPhoneContactMember(contact: PhoneContact) {

        contactsAddedAdapter.addPhoneContact(contact)
        binding.fragmentGroupCreationMembersAddedList.smoothScrollToPosition(contactsAddedAdapter.itemCount)

    }

    override fun onPhoneContactMemberRemoved(view: View, contact: PhoneContact, position: Int) {

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

    private fun navigateToDashboard() {
        findNavController().popBackStack(R.id.groupCreationMainInfoFragment, true)
    }

    override fun needToHideNavigationBar(): Boolean = true
}
