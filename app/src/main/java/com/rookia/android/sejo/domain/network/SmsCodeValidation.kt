package com.rookia.android.sejo.domain.network


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

data class SmsCodeValidation(
    val phonePrefix: String,
    val phoneNumber: String,
    val smsCode: String
)
 