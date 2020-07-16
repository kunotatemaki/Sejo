package com.rookia.android.sejo.domain.network

import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.domain.local.smscode.SmsCodeValidation
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.domain.network.group.CreateGroupClient
import com.rookia.android.sejo.domain.network.group.RequestGroupsServer
import com.rookia.android.sejo.domain.network.login.LoginRequestServer
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationServer
import com.rookia.android.sejo.domain.network.user.UserCreationRequestServer
import com.rookia.android.sejo.framework.utils.DateUtils


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
    SmsCodeValidation(result = code, userId = data.userId, lastUsedGroup = data.lastUsedGroup)

fun LoginRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = code, token = data?.token, userId = null)

fun UserCreationRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = code, token = data?.token, userId = data?.userId)

fun PhoneContact.toCreateGroupContact(): CreateGroupClient.Contact =
    CreateGroupClient.Contact(numberId = phoneNumberNormalized)

fun RequestGroupsServer.GroupServer.toGroup(dateUtils: DateUtils): Group =
    Group(
        groupId = groupId,
        name = name,
        fee = fee,
        owner = owner,
        dateCreation = dateUtils.convertZuluTimeToUTCTimestamp(dateCreation),
        balance = balance,
        dateModification = dateUtils.convertZuluTimeToUTCTimestamp(dateModification),
        members = members.map { it.toGroupContact() }

    )

fun RequestGroupsServer.GroupContactServer.toGroupContact(): Group.GroupContact =
    Group.GroupContact(
        memberStatus = memberStatus, numberId = numberId, isAdmin = isAdmin
    )

