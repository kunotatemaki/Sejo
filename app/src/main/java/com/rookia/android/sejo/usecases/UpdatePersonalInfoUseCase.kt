package com.rookia.android.sejo.usecases

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocoreandroid.data.repository.UserRepository
import com.rookia.android.sejocoreandroid.domain.local.User
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

class UpdatePersonalInfoUseCase @Inject constructor(private val repository: UserRepository) {

    fun updateUser(user: User): Flow<Result<Int>> =
        repository.updateUser(user)

}