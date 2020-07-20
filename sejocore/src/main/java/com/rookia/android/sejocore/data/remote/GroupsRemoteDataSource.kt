package com.rookia.android.sejocore.data.remote


import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.domain.local.Group
import com.rookia.android.sejocore.domain.local.PhoneContact

/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, July 2020
 *
 *
 */

interface GroupsRemoteDataSource {

    suspend fun createGroupInServer(
        name: String,
        fee: Int,
        owner: String,
        members: List<PhoneContact>
    ): Result<Unit>

    suspend fun getGroupsFromServer(userId: String): Result<List<Group>>

}