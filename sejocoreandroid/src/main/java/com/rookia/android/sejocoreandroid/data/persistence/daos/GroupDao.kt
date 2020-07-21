package com.rookia.android.sejocoreandroid.data.persistence.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.rookia.android.androidutils.persistence.daos.BaseDao
import com.rookia.android.sejocoreandroid.data.persistence.entities.GroupEntity
import com.rookia.android.sejocoreandroid.data.persistence.model.GroupWithMembers
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

@Dao
abstract class GroupDao: BaseDao<GroupEntity>() {

    @Transaction
    @Query("SELECT * FROM `group` WHERE group_id = :groupId")
    abstract fun getGroup(groupId: Long): Flow<GroupWithMembers>

    @Transaction
    @Query("SELECT * FROM `group` ORDER BY date_modification DESC")
    abstract fun getAllGroups(): Flow<List<GroupWithMembers>>

}