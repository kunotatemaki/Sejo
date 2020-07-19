package com.rookia.android.sejocore.data.repository

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.kotlinutils.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejocore.data.remote.LoginRemoteDataSource
import com.rookia.android.sejocore.domain.local.TokenReceived
import kotlinx.coroutines.flow.Flow

/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, July 2020
 *
 *
 */

class LoginRepository constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) {

    fun login(
        userId: String,
        pin: Int,
        pushToken: String?
    ): Flow<Result<TokenReceived>> =
        resultOnlyFromOneSourceInFlow {
            loginRemoteDataSource.loginInServer(userId, pin, pushToken)
        }

}