package com.rookia.android.sejocoreandroid.data.datasources

import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejocore.data.local.GroupsLocalDataSource
import com.rookia.android.sejocore.domain.local.Group
import com.rookia.android.sejocoreandroid.data.persistence.PersistenceManager
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

class GroupsLocalDataSourceImp @Inject constructor(
    private val persistenceManager: PersistenceManager,
    private val preferencesManager: PreferencesManager
): GroupsLocalDataSource {

    companion object{
        const val LAST_CHECKED_TIMESTAMP = "LAST_CHECKED_TIMESTAMP_FOR_GROUPS"
    }
    override suspend fun saveGroups(groups: List<Group>) {
        persistenceManager.saveGroups(groups)
    }

    override fun saveLastRequestedTime(timestamp: Long) {
        preferencesManager.setLongIntoPreferences(
            LAST_CHECKED_TIMESTAMP,
            timestamp
        )
    }

    override fun getLastRequestedTime(): Long =
        preferencesManager.getLongFromPreferences(LAST_CHECKED_TIMESTAMP, 0L)


    override fun getGroup(groupId: Long): Flow<Group> =
        persistenceManager.getGroup(groupId)

    override fun getGroups(): Flow<List<Group>> =
        persistenceManager.getGroups()
}