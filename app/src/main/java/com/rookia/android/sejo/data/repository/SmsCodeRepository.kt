package com.rookia.android.sejo.data.repository

import androidx.lifecycle.LiveData
import com.rookia.android.androidutils.domain.vo.Result
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

interface SmsCodeRepository {
    fun askForSmsCode(phonePrefix: String, phoneNumber: String): Flow<Result<Int>>
    fun validateSmsCode(phonePrefix: String, phoneNumber: String, smsCode: String): Flow<Result<Int>>
}