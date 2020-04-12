package com.rookia.android.sejo.usecases

import androidx.lifecycle.LiveData
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.data.repository.SmsCodeRepository
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

class ValidateSmsCodeUseCase constructor(private val repository: SmsCodeRepository) {

    fun validateSmsCode(phonePrefix: String, phoneNumber: String, smsCode: String): Flow<Result<Int>> =
        repository.validateSmsCode(phonePrefix, phoneNumber, smsCode)

}