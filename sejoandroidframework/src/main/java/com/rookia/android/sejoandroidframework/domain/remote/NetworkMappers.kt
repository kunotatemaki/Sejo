package com.rookia.android.sejoandroidframework.domain.remote

import com.rookia.android.sejocore.domain.local.SmsCodeValidation
import com.rookia.android.sejocore.domain.local.TokenReceived


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, July 2020
 *
 *
 */

fun SmsCodeValidationServer.toSmsCodeValidation(): SmsCodeValidation =
    SmsCodeValidation(result = code, userId = data.userId, lastUsedGroup = data.lastUsedGroup)

fun LoginRequestServer.toTokenReceived(): TokenReceived =
    TokenReceived(result = code, token = data?.token, userId = null)

//fun UserCreationRequestServer.toTokenReceived(): TokenReceived =
//    TokenReceived(result = code, token = data?.token, userId = data?.userId)

//fun PhoneContact.toCreateGroupContact(): CreateGroupClient.Contact =
//    CreateGroupClient.Contact(numberId = phoneNumberNormalized)
//
//fun RequestGroupsServer.GroupServer.toGroup(dateUtils: DateUtils): Group =
//    Group(
//        groupId = groupId,
//        name = name,
//        fee = fee,
//        owner = owner,
//        dateCreation = dateUtils.convertZuluTimeToUTCTimestamp(dateCreation),
//        balance = balance,
//        dateModification = dateUtils.convertZuluTimeToUTCTimestamp(dateModification),
//        members = members.map { it.toGroupContact() }
//
//    )
//
//fun RequestGroupsServer.GroupContactServer.toGroupContact(): Group.GroupContact =
//    Group.GroupContact(
//        memberStatus = memberStatus, numberId = numberId, isAdmin = isAdmin
//    )
//
//