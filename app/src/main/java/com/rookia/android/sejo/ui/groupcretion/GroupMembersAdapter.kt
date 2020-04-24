package com.rookia.android.sejo.ui.groupcretion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rookia.android.sejo.databinding.ComponentPhoneContactBinding
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

class GroupMembersAdapter : RecyclerView.Adapter<GroupMembersAdapter.GroupMemberViewHolder>() {

    private var phoneContacts: List<PhoneContact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        val binding =
            ComponentPhoneContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupMemberViewHolder(binding)
    }

    override fun getItemCount(): Int = phoneContacts.size

    override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        holder.bind(contact = phoneContacts[position])
    }

    fun setPhoneContacts(list: List<PhoneContact>?){
        list?.let {
            phoneContacts = list
            notifyDataSetChanged()
        }
    }

    class GroupMemberViewHolder(private val binding: ComponentPhoneContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: PhoneContact) {
            binding.contact = contact
//            if(contact.photoUrl == null){
//                binding.componentPhoneContactImage.setImageResource(R.drawable.ic_contact)
//            } else {
//                BindingAdapters.setImageUrlRounded(binding.componentPhoneContactImage, contact.photoUrl, null)
//            }
        }
    }
}

