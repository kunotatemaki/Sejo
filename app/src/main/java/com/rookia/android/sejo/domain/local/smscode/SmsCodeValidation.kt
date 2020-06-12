package com.rookia.android.sejo.domain.local.smscode


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
    val result: Int,
    val userId: String?,
    val lastUsedGroup: Int?
)
 