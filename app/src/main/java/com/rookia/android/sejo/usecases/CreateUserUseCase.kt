package com.rookia.android.sejo.usecases

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.data.repository.UserRepository
import com.rookia.android.sejocore.domain.local.TokenReceived
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

class CreateUserUseCase @Inject constructor(private val repository: UserRepository) {

    fun createUser(
        phonePrefix: String,
        phoneNumber: String,
        pin: Int
    ): Flow<Result<TokenReceived>> =
        repository.createUser(phonePrefix, phoneNumber, pin)

}