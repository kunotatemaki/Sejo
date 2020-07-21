package com.rookia.android.sejo.ui.groupcreation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.resources.ResourcesManager
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ElementPhoneContactThumbnailBinding
import com.rookia.android.sejocoreandroid.domain.local.PhoneContact


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */

class GroupCreationMembersAddedAdapter constructor(
    private val listener: GroupMemberRemovedList,
    private val resourcesManager: ResourcesManager
) : RecyclerView.Adapter<GroupCreationMembersAddedAdapter.GroupMemberViewHolder>() {

    private val phoneContacts: MutableList<PhoneContact> = mutableListOf()
    private val positionsToAnimateWhenAdded = mutableListOf<Int>()

    interface GroupMemberRemovedList {
        fun onPhoneContactMemberRemoved(view: View, contact: PhoneContact, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        val binding =
            ElementPhoneContactThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GroupMemberViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = phoneContacts.size + 1

    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        val animate = positionsToAnimateWhenAdded.contains(position)
        if (position == 0) {
            holder.bindMyself(resourcesManager.getString(R.string.component_phone_contact_thumbnail_owner))
        } else {
            holder.bind(contact = phoneContacts[position - 1])
        }
        if (animate) {
            setExpandAnimation(holder.itemView)
            positionsToAnimateWhenAdded.remove(position)
        }
    }

    private fun setExpandAnimation(viewToAnimate: View) {
        val animation: Animation =
            AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.expand)
        viewToAnimate.startAnimation(animation)
    }

    fun addPhoneContact(contact: PhoneContact) {
        if (!phoneContacts.contains(contact)) {
            positionsToAnimateWhenAdded.add(phoneContacts.size + 1)
            phoneContacts.add(contact)
            notifyItemInserted(phoneContacts.lastIndex + 1)
        }
    }

    fun removePhoneContact(contact: PhoneContact, position: Int) {
        phoneContacts.remove(contact)
        notifyItemRemoved(position)
    }

    fun getContactsAdded(): List<PhoneContact> = phoneContacts

    fun addListOfContacts(contacts: List<PhoneContact>?) {
        contacts?.let {
            positionsToAnimateWhenAdded.clear()
            phoneContacts.clear()
            phoneContacts.addAll(contacts)
            notifyDataSetChanged()
        }
    }

    class GroupMemberViewHolder(
        private val binding: ElementPhoneContactThumbnailBinding,
        private val listener: GroupMemberRemovedList
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: PhoneContact) {
            with(binding) {
                root.visible()
                componentPhoneContactClose.visible()
                this.name = contact.name
                this.photoUrl = contact.photoUrl
                root.setOnClickListener {
                    val positionInList =
                        if (adapterPosition != RecyclerView.NO_POSITION) adapterPosition else layoutPosition
                    listener.onPhoneContactMemberRemoved(it, contact, positionInList)
                }

            }
        }

        fun bindMyself(myName: String) {
            with(binding) {
                root.visible()
                componentPhoneContactClose.gone()
                this.name = myName
                this.photoUrl = null
                root.setOnClickListener(null)

            }
        }
    }
}

