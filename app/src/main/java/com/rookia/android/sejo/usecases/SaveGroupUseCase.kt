package com.rookia.android.sejo.usecases

import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.domain.local.Group


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */

class SaveGroupUseCase constructor(
    private val repository: GroupRepository
) {

    suspend fun saveGroup(group: Group) {
        repository.saveGroup(group)
    }

}