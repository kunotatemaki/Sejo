package com.rookia.android.sejo.domain.network

import com.rookia.android.sejo.domain.local.smscode.SmsCodeValidation
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationServer


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
 
fun SmsCodeValidationServer.toSmsCodeValidation() =
    SmsCodeValidation(result = this.result, userExists = this.userExists)