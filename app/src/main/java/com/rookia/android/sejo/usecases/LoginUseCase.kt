package com.rookia.android.sejo.usecases

import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.data.repository.LoginRepository
import com.rookia.android.sejo.domain.local.user.TokenReceived
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

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    fun login(userId: String, pin: Int, pushToken: String?): Flow<Result<TokenReceived>> =
        repository.login(userId, pin, pushToken)

}