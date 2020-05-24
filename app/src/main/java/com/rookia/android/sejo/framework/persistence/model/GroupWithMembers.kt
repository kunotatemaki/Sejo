package com.rookia.android.sejo.framework.persistence.model

import androidx.room.Embedded
import androidx.room.Relation
import com.rookia.android.sejo.framework.persistence.entities.GroupEntity
import com.rookia.android.sejo.framework.persistence.entities.MemberEntity


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

data class GroupWithMembers (
    @Embedded
    val group: GroupEntity,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "group_id"
    )
    val members: List<MemberEntity>
)
