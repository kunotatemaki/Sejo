package com.rookia.android.sejocore.data.local

import com.rookia.android.sejocore.domain.local.Group
import kotlinx.coroutines.flow.Flow


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

interface GroupsLocalDataSource {

    suspend fun saveGroups(groups: List<Group>)

    fun saveLastRequestedTime(timestamp: Long)

    fun getLastRequestedTime(): Long

    fun getGroup(groupId: Long): Flow<Group>

    fun getGroups(): Flow<List<Group>>

}