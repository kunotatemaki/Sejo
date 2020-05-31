package com.rookia.android.sejo.data.repository

import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.domain.local.user.User
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

interface UserRepository {
    fun createUser(phonePrefix: String, phoneNumber: String, pin: Int): Flow<Result<TokenReceived>>
    fun updateUser(user: User): Flow<Result<Int>>
    fun login(userId: String, pin: Int, pushToken: String?): Flow<Result<TokenReceived>>
    fun storeToken(token: String)
}