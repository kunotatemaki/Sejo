package com.rookia.android.sejo.data.persistence

import com.rookia.android.sejo.domain.local.Group


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

interface PersistenceManager {
    suspend fun saveGroups(groups: List<Group>)
    fun getGroups(): List<Group>
}