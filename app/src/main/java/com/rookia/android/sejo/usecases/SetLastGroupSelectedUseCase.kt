package com.rookia.android.sejo.usecases

import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, June 2020
 *
 *
 */

class SetLastGroupSelectedUseCase @Inject constructor(private val repository: UserRepository) {

    fun updateUser(userId: String, groupId: Long): Flow<Result<Int>> =
        repository.updateLastGroupSelected(userId, groupId)

}