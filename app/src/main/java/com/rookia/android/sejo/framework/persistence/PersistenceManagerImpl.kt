package com.rookia.android.sejo.framework.persistence

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
                member.toEntity(group.groupId ?: 0)
            }
        }
        db.membersDao().insert(contacts)
    }

    override fun getGroups(): List<Group> =
        db.groupDao().getAllGroups().map { group -> group.toGroup() }

}