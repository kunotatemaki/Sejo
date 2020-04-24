package com.rookia.android.sejo.ui.groupcretion


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ComponentPhoneContactThumbnailBinding
import com.rookia.android.sejo.domain.local.PhoneContact


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

class GroupMembersAddedAdapter constructor(private val listener: GroupMemberAddedList): RecyclerView.Adapter<GroupMembersAddedAdapter.GroupMemberViewHolder>() {

    private val phoneContacts: MutableList<PhoneContact> = mutableListOf()
    private val positionsToAnimateWhenAdded = mutableListOf<Int>()

    interface GroupMemberAddedList {
        fun onPhoneContactMemberAdded(view: View, contact: PhoneContact, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        val binding =
            ComponentPhoneContactThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GroupMemberViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = phoneContacts.size

    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        val animate = positionsToAnimateWhenAdded.contains(position)
        holder.bind(contact = phoneContacts[position])
        if(animate) {
            setExpandAnimation(holder.itemView, position)
            positionsToAnimateWhenAdded.remove(position)
        }
    }

    private fun setExpandAnimation(viewToAnimate: View, position: Int) {
        val animation: Animation =
            AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.expand)
        viewToAnimate.startAnimation(animation)
    }

    fun addPhoneContact(contact: PhoneContact) {
        if(!phoneContacts.contains(contact)) {
            positionsToAnimateWhenAdded.add(phoneContacts.size)
            phoneContacts.add(contact)
            notifyItemInserted(phoneContacts.lastIndex)
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

    class GroupMemberViewHolder(private val binding: ComponentPhoneContactThumbnailBinding, private val listener: GroupMemberAddedList) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: PhoneContact) {
            binding.root.visible()
            binding.contact = contact
            binding.root.setOnClickListener {
                val position = if (adapterPosition != RecyclerView.NO_POSITION) adapterPosition else layoutPosition
                listener.onPhoneContactMemberAdded(it, contact, position)
            }
        }
    }
}

