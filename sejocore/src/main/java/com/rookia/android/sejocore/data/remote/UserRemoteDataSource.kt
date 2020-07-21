package com.rookia.android.sejocore.data.remote

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.domain.local.TokenReceived
import com.rookia.android.sejocore.domain.local.User


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
 
 interface UserRemoteDataSource {

    suspend fun createUserInServer(
        phonePrefix: String,
        phoneNumber: String,
        pin: Int
    ): Result<TokenReceived>

    suspend fun updateUserInServer(user: User): Result<Int>

    suspend fun setLastGroupUsed(userId: String, groupId: Long): Result<Int>

}