package com.rookia.android.sejo.framework.persistence

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.rookia.android.sejo.data.persistence.PersistenceManager
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.framework.persistence.databases.AppDatabase
import com.rookia.android.sejo.framework.persistence.entities.MemberEntity
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
    override suspend fun saveGroups(groups: List<Group>) {
        db.groupDao().insert(groups.map { it.toEntity() })
        val contacts = mutableListOf<MemberEntity>()
        groups.flatMapTo(
            contacts
        ) { group ->
            group.members.map { member ->
                member.toEntity(group.groupId)
            }
        }
        db.membersDao().insert(contacts)
    }

    override fun getGroups(): LiveData<PagedList<Group>> =
        db.groupDao().getAllGroups()
            .map { it.toGroup() }
            .toLiveData(pageSize = 10)

    @Suppress("UNNECESSARY_SAFE_CALL")
    override fun getGroup(groupId: Long): LiveData<Group> =
        db.groupDao().getGroup(groupId).map {
            it?.toGroup()
        }


}