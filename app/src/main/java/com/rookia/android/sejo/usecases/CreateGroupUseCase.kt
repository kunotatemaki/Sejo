package com.rookia.android.sejo.usecases

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocoreandroid.data.repository.GroupsRepository
import com.rookia.android.sejocoreandroid.domain.local.PhoneContact
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

class CreateGroupUseCase @Inject constructor(
    private val repository: GroupsRepository
) {

    fun createGroup(
        name: String,
        fee: Int,
        owner: String,
        members: List<PhoneContact>
    ): Flow<Result<Unit>> =
        repository.createGroup(name, fee, owner, members)

}