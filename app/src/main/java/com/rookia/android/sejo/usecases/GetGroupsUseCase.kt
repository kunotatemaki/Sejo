package com.rookia.android.sejo.usecases

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.data.repository.GroupsRepository
import com.rookia.android.sejocore.domain.local.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


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

class GetGroupsUseCase @Inject constructor(
    private val repository: GroupsRepository
) {

    fun getGroups(userId: String): Flow<Result<List<Group>>> =
        repository.getGroups(userId)

    fun getGroup(groupId: Long): Flow<Group> =
        repository.getGroup(groupId)

}