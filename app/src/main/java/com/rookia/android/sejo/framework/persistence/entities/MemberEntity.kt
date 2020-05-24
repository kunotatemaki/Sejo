package com.rookia.android.sejo.framework.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


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

@Entity(tableName = "member", indices = [(Index(value = arrayOf("group_id"), unique = true))])
class MemberEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "group_id")
    val groupId: Int,
    @ColumnInfo(name = "number_id")
    val numberId: String,
    @ColumnInfo(name = "admin")
    val admin: Boolean,
    @ColumnInfo(name = "member_status")
    val memberStatus: Int

)