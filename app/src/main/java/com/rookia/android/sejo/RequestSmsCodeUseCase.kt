package com.rookia.android.sejo

import androidx.lifecycle.LiveData
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.data.repository.SmsCodeRepository


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

class RequestSmsCodeUseCase constructor(private val repository: SmsCodeRepository) {

    fun askForSmsCode(phonePrefix: String, phoneNumber: String): LiveData<Result<Int>> =
        repository.askForSmsCode(phonePrefix, phoneNumber)

}