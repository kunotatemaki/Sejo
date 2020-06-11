package com.rookia.android.sejo.usecases

import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.domain.local.Group
import kotlinx.coroutines.flow.Flow


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

class GetGroupsUseCase constructor(
    private val repository: GroupRepository
) {

    fun getGroups(
        userId: String,
        lastCheckedDate: Long
    ): Flow<Result<List<Group>>> =
        repository.getGroups(userId, lastCheckedDate)

}