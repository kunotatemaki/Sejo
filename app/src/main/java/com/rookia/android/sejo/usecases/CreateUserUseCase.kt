package com.rookia.android.sejo.usecases

import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.data.repository.UserRepository
import com.rookia.android.sejo.domain.local.user.TokenReceived
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

class CreateUserUseCase constructor(private val repository: UserRepository) {

    fun createUser(
        phonePrefix: String,
        phoneNumber: String,
        pin: Int
    ): Flow<Result<TokenReceived>> =
        repository.createUser(phonePrefix, phoneNumber, pin)

}