package com.rookia.android.sejo.framework

import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.framework.persistence.entities.GroupEntity
import com.rookia.android.sejo.framework.persistence.entities.MemberEntity
import com.rookia.android.sejo.framework.persistence.model.GroupWithMembers


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
        groupId = groupId,
        name = name,
        fee = fee,
        owner = owner,
        balance = balance,
        date = date
    )

fun GroupEntity.toGroup(membert: List<Group.Member>): Group =
    Group(
        groupId = groupId,
        name = name,
        fee = fee,
        owner = owner,
        balance = balance,
        date = date,
        members = membert
    )

fun Group.Member.toEntity(groupId: Int): MemberEntity =
    MemberEntity(
        groupId = groupId,
        numberId = numberId,
        admin = isAdmin,
        memberStatus = memberStatus
    )

fun MemberEntity.toEntity(): Group.Member =
    Group.Member(numberId = numberId, isAdmin = admin, memberStatus = memberStatus)

fun GroupWithMembers.toGroup(): Group {
    val members = this.members.map { it.toEntity() }
    return group.toGroup(members)
}