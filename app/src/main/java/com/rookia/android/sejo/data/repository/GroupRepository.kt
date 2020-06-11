package com.rookia.android.sejo.data.repository

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
    fun getGroups(userId: String, lastCheckedDate: Long): Flow<Result<List<Group>>>
}