package com.rookia.android.sejo.usecases

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.data.repository.SmsCodeRepository
import com.rookia.android.sejocore.domain.local.SmsCodeValidation
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

class SmsCodeUseCases @Inject constructor(private val repository: SmsCodeRepository) {

    fun askForSmsCode(
        phonePrefix: String,
        phoneNumber: String
    ): Flow<Result<Int>> =
        repository.askForSmsCode(phonePrefix, phoneNumber)

    fun validateSmsCode(phonePrefix: String, phoneNumber: String, smsCode: String): Flow<Result<SmsCodeValidation>> =
        repository.validateSmsCode(phonePrefix, phoneNumber, smsCode)

}