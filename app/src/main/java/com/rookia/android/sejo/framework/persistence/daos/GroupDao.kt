package com.rookia.android.sejo.framework.persistence.daos

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.rookia.android.androidutils.framework.persistence.daos.BaseDao
import com.rookia.android.sejo.framework.persistence.entities.GroupEntity
import com.rookia.android.sejo.framework.persistence.model.GroupWithMembers


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

//    @Transaction
//    @Query("SELECT * FROM `group` WHERE group_id = :groupId")
//    abstract fun getGroupWithMembers(groupId: Long): LiveData<GroupWithMembers>

    @Query("SELECT * FROM `group` WHERE group_id = :groupId")
    abstract fun getGroup(groupId: Long): LiveData<GroupEntity>

    @Transaction
    @Query("SELECT * FROM `group` ORDER BY date_modification DESC")
    abstract fun getAllGroups(): DataSource.Factory<Int, GroupWithMembers>

}