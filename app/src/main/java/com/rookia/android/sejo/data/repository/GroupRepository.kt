package com.rookia.android.sejo.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.domain.local.PhoneContact
import kotlinx.coroutines.flow.Flow


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, May 2020
 *
 *
 */

interface GroupRepository {
    fun createGroup(
        name: String,
        fee: Int,
        owner: String,
        members: List<PhoneContact>
    ): Flow<Result<Unit>>

    suspend fun saveGroups(groups: List<Group>)
    fun getGroups(userId: String): LiveData<Result<PagedList<Group>>>
    fun getGroup(groupId: Long): LiveData<Group>
//    fun getGroupWithMembers(groupId: Long): LiveData<GroupWithMembers>
}