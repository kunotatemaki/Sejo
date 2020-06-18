package com.rookia.android.sejo.framework

import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.framework.persistence.entities.GroupEntity
import com.rookia.android.sejo.framework.persistence.entities.MemberEntity
import com.rookia.android.sejo.framework.persistence.model.GroupWithMembers
import java.util.*


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, May 2020
 *
 *
 */

fun Group.toEntity(): GroupEntity =
    GroupEntity(
        groupId = groupId ?: 0,
        name = name,
        fee = fee,
        owner = owner,
        balance = balance,
        dateCreation = Date(dateCreation ?: 0L),
        dateModification = Date(dateModification ?: 0L)
    )

fun GroupEntity.toGroup(member: List<Group.GroupContact>): Group =
    Group(
        groupId = groupId,
        name = name,
        fee = fee,
        owner = owner,
        balance = balance,
        dateCreation = dateCreation.time,
        members = member,
        dateModification = dateModification.time
    )

fun Group.GroupContact.toEntity(groupId: Long): MemberEntity =
    MemberEntity(
        groupId = groupId,
        numberId = numberId,
        admin = isAdmin ?: false,
        memberStatus = memberStatus ?: Constants.MemberStates.GUESS.code
    )

fun MemberEntity.toEntity(): Group.GroupContact =
    Group.GroupContact(numberId = numberId, isAdmin = admin, memberStatus = memberStatus)

fun GroupWithMembers.toGroup(): Group {
    val members = this.members.map { it.toEntity() }
    return group.toGroup(members)
}