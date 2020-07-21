package com.rookia.android.sejocoreandroid.data.datasources.local

import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.sejocoreandroid.data.persistence.PersistenceManager
import com.rookia.android.sejocoreandroid.domain.local.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


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

class GroupsLocalDataSource @Inject constructor(
    private val persistenceManager: PersistenceManager,
    private val preferencesManager: PreferencesManager
) {

    companion object{
        const val LAST_CHECKED_TIMESTAMP = "LAST_CHECKED_TIMESTAMP_FOR_GROUPS"
    }

    suspend fun saveGroups(groups: List<Group>) {
        persistenceManager.saveGroups(groups)
    }

    fun saveLastRequestedTime(timestamp: Long) {
        preferencesManager.setLongIntoPreferences(
            LAST_CHECKED_TIMESTAMP,
            timestamp
        )
    }

    fun getLastRequestedTime(): Long =
        preferencesManager.getLongFromPreferences(LAST_CHECKED_TIMESTAMP, 0L)


    fun getGroup(groupId: Long): Flow<Group> =
        persistenceManager.getGroup(groupId)

    fun getGroups(): Flow<List<Group>> =
        persistenceManager.getGroups()
}