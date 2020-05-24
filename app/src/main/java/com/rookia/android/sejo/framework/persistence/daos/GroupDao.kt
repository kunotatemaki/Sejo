package com.rookia.android.sejo.framework.persistence.daos

import androidx.lifecycle.LiveData
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

    @Transaction
    @Query("SELECT * FROM `group` WHERE group_id = :groupId")
    abstract fun getGroup(groupId: Int): GroupWithMembers?

    @Transaction
    @Query("SELECT * FROM `group`")
    abstract fun getAllGroups(): LiveData<List<GroupWithMembers>>

}