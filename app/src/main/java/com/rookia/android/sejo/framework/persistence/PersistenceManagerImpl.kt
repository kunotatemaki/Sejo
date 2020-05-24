package com.rookia.android.sejo.framework.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.rookia.android.sejo.data.persistence.PersistenceManager
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.framework.persistence.databases.AppDatabase
import com.rookia.android.sejo.framework.toEntity
import com.rookia.android.sejo.framework.toGroup
import javax.inject.Inject


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

class PersistenceManagerImpl @Inject constructor(private val db: AppDatabase) : PersistenceManager {
    override suspend fun saveGroup(group: Group) {
        db.groupDao().insert(group.toEntity())
        db.membersDao().insert(group.members.map { it.toEntity(groupId = group.groupId) })
    }

    override fun getGroups(): LiveData<List<Group>> =
        db.groupDao().getAllGroups().switchMap { list ->
            MutableLiveData(list.map { group -> group.toGroup() })
        }
}