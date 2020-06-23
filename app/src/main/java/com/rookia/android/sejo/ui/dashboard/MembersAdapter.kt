package com.rookia.android.sejo.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rookia.android.sejo.databinding.ElementMemberBinding
import com.rookia.android.sejo.domain.local.Group


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, June 2020
 *
 *
 */

class MembersAdapter (private val members: List<Group.GroupContact>, private val listener: GroupMemberInAdapter): RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {

    interface GroupMemberInAdapter{
        fun onGroupMemberClicked(member: Group.GroupContact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementMemberBinding.inflate(inflater, parent, false)
        return MemberViewHolder(binding)
    }

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(members[position], listener)
    }


    class MemberViewHolder(private val binding: ElementMemberBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(member: Group.GroupContact, listener: GroupMemberInAdapter){
            binding.member = member
            binding.root.setOnClickListener {
                listener.onGroupMemberClicked(member)
            }
        }
    }
}