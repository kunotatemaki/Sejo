package com.rookia.android.sejocore.data.remote

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.domain.local.SmsCodeValidation


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

interface SmsCodeRemoteDataSource {

    suspend fun requestSmsFromServer(phonePrefix: String, phoneNumber: String): Result<Int>

    suspend fun validateSmsFromServer(
        phonePrefix: String,
        phoneNumber: String,
        smsCode: String
    ): Result<SmsCodeValidation>

}