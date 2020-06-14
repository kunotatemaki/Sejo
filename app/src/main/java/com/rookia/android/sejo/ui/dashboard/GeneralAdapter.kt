package com.rookia.android.sejo.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rookia.android.sejo.databinding.ItemGroupBinding
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

class GeneralAdapter : PagedListAdapter<Group, GroupViewHolder>(DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Group> = object : DiffUtil.ItemCallback<Group>() {


            override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean =
                oldItem.groupId == newItem.groupId

            override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGroupBinding.inflate(inflater, parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }
}

class GroupViewHolder constructor(private val binding: ItemGroupBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(group: Group?) {
        group?.let {
            binding.group.text = group.groupId.toString()

        }
    }
}