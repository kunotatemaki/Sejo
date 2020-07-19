package com.rookia.android.sejocore.data.remote


import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.domain.local.TokenReceived

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

interface LoginRemoteDataSource {

    suspend fun loginInServer(
        userId: String,
        pin: Int,
        pushToken: String?
    ): Result<TokenReceived>

}