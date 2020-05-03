package com.rookia.android.sejo.domain.network

import com.rookia.android.sejo.domain.local.smscode.SmsCodeValidation
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.domain.local.user.User
import com.rookia.android.sejo.domain.network.login.LoginRequestServer
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationServer
import com.rookia.android.sejo.domain.network.user.UserCreationRequestServer
import com.rookia.android.sejo.domain.network.user.UserUpdateRequestClient


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
 
fun SmsCodeValidationServer.toSmsCodeValidation(): SmsCodeValidation =
    SmsCodeValidation(result = this.code, userExists = this.data.userExists)

fun LoginRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = this.code, token = this.data?.token, userId = null)

fun UserCreationRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = this.code, token = this.data?.token, userId = this.data?.userId)

fun User.toUserUpdateRequestClient(): UserUpdateRequestClient =
    UserUpdateRequestClient(pin = null, userId = userId, name = this.name)

fun User.toUserUpdateRequestClient(pin: Int): UserUpdateRequestClient =
    UserUpdateRequestClient(pin = pin, userId = userId, name = this.name)
