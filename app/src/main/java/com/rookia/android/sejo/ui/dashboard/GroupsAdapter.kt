package com.rookia.android.sejo.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rookia.android.sejo.databinding.ElementGroupBinding
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

class GroupsAdapter constructor(private val listener: GroupItemClickable) :
    PagedListAdapter<Group, GroupViewHolder>(DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Group> = object : DiffUtil.ItemCallback<Group>() {

            override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean =
                oldItem.groupId == newItem.groupId

            override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean =
                oldItem == newItem
        }
    }

    interface GroupItemClickable {
        fun onGroupClicked(groupId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementGroupBinding.inflate(inflater, parent, false)
        return GroupViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }

}

class GroupViewHolder constructor(
    private val binding: ElementGroupBinding,
    private val listener: GroupsAdapter.GroupItemClickable
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(group: Group?) {
        group?.let {
            binding.group = it
            itemView.setOnClickListener { _ ->
                listener.onGroupClicked(it.groupId)
            }
        }
    }
}