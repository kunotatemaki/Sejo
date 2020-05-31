package com.rookia.android.sejo.domain.network

import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.domain.local.smscode.SmsCodeValidation
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.domain.local.user.User
import com.rookia.android.sejo.domain.network.group.CreateGroupClient
import com.rookia.android.sejo.domain.network.group.CreateGroupServer
import com.rookia.android.sejo.domain.network.login.LoginRequestServer
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationServer
import com.rookia.android.sejo.domain.network.user.UserCreationRequestServer
import com.rookia.android.sejo.domain.network.user.UserUpdateRequestClient
import java.util.*


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
    SmsCodeValidation(result = code, userId = data.userId)

fun LoginRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = code, token = data?.token, userId = null)

fun UserCreationRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = code, token = data?.token, userId = data?.userId)

fun User.toUserUpdateRequestClient(
    pin: Int? = null,
    pushToken: String? = null
): UserUpdateRequestClient =
    UserUpdateRequestClient(pin = pin, userId = userId, name = name, pushToken = pushToken)

fun PhoneContact.toCreateGroupContact(): CreateGroupClient.Contact =
    CreateGroupClient.Contact(numberId = phoneNumberNormalized)

fun CreateGroupServer.CreateGroupResponse.toGroup(): Group =
    Group(
        groupId = groupId,
        name = name,
        fee = fee,
        owner = owner,
        balance = balance,
        date = Date(date),
        members = members.map { it.toMember() }
    )

fun CreateGroupServer.Member.toMember(): Group.Member =
    Group.Member(
        numberId = numberId,
        memberStatus = memberStatus ?: Constants.MemberStates.GUESS.code,
        isAdmin = isAdmin
    )