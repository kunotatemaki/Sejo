package com.rookia.android.sejocoreandroid.data.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

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

@Entity(
    tableName = "member",
    primaryKeys = ["group_id", "number_id"],
    indices = [(Index(value = arrayOf("group_id"), unique = false))])
class MemberEntity constructor(
    @ColumnInfo(name = "group_id")
    val groupId: Long,
    @ColumnInfo(name = "number_id")
    val numberId: String,
    @ColumnInfo(name = "admin")
    val admin: Boolean,
    @ColumnInfo(name = "member_status")
    val memberStatus: Int

)