package com.rookia.android.sejo.data.repository

import com.rookia.android.androidutils.domain.vo.Result
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

interface LoginRepository {
    fun login(userId: String, pin: Int, pushToken: String?): Flow<Result<TokenReceived>>
}