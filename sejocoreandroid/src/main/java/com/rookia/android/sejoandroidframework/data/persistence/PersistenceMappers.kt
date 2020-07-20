package com.rookia.android.sejoandroidframework.data.persistence

import com.rookia.android.sejoandroidframework.data.persistence.entities.GroupEntity
import com.rookia.android.sejoandroidframework.data.persistence.entities.MemberEntity
import com.rookia.android.sejoandroidframework.data.persistence.model.GroupWithMembers
import com.rookia.android.sejocore.domain.local.Group
import com.rookia.android.sejocore.domain.local.MemberStates


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

fun Group.toEntity(): GroupEntity =
    GroupEntity(
        groupId = groupId,
        name = name,
        fee = fee,
        owner = owner,
        balance = balance,
        dateCreation = dateCreation,
        dateModification = dateModification
    )

fun GroupEntity.toGroup(member: List<Group.GroupContact>): Group =
    Group(
        groupId = groupId,
        name = name,
        fee = fee,
        owner = owner,
        balance = balance,
        dateCreation = dateCreation,
        members = member,
        dateModification = dateModification
    )

fun Group.GroupContact.toEntity(groupId: Long): MemberEntity =
    MemberEntity(
        groupId = groupId,
        numberId = numberId,
        admin = isAdmin ?: false,
        memberStatus = memberStatus ?: MemberStates.GUESS.code
    )

fun MemberEntity.toEntity(): Group.GroupContact =
    Group.GroupContact(numberId = numberId, isAdmin = admin, memberStatus = memberStatus)

fun GroupWithMembers.toGroup(): Group {
    val members = this.members.map { it.toEntity() }
    return group.toGroup(members)
}