package com.rookia.android.sejocoreandroid.data.persistence

import com.rookia.android.sejocoreandroid.data.persistence.databases.AppDatabase
import com.rookia.android.sejocoreandroid.data.persistence.entities.MemberEntity
import com.rookia.android.sejocoreandroid.domain.local.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

class PersistenceManager @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun saveGroups(groups: List<Group>) {
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

    fun getGroups(): Flow<List<Group>> =
        db.groupDao().getAllGroups()
            .map { list ->
                list.map { group ->
                    group.toGroup()
                }
            }

    @Suppress("UNNECESSARY_SAFE_CALL")
    fun getGroup(groupId: Long): Flow<Group> =
        db.groupDao().getGroup(groupId).map {
            it?.toGroup()
        }


}