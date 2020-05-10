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
    SmsCodeValidation(result = this.code, userId = this.data.userId)

fun LoginRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = this.code, token = this.data?.token, userId = null)

fun UserCreationRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = this.code, token = this.data?.token, userId = this.data?.userId)

fun User.toUserUpdateRequestClient(): UserUpdateRequestClient =
    UserUpdateRequestClient(pin = null, userId = userId, name = this.name)

fun User.toUserUpdateRequestClient(pin: Int): UserUpdateRequestClient =
    UserUpdateRequestClient(pin = pin, userId = userId, name = this.name)

fun PhoneContact.toCreateGroupContact(): CreateGroupClient.Contact =
    CreateGroupClient.Contact(this.phoneNumberNormalized)

fun CreateGroupServer.CreateGroupResponse.toGroup(): Group =
    Group(this.groupId, this.name, this.fee, this.owner, this.balance, this.members.map { it.toMember() })

fun CreateGroupServer.Member.toMember(): Group.Member =
    Group.Member(this.numberId, this.memberStatus ?: Constants.MemberStates.GUESS.code)