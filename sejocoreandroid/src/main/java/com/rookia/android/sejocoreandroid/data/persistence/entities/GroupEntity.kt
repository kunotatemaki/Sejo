package com.rookia.android.sejocoreandroid.data.persistence.entities

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

@Entity(tableName = "group", indices = [(Index(value = arrayOf("group_id"), unique = true))])
class GroupEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "group_id")
    val groupId: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "fee")
    val fee: Float,
    @ColumnInfo(name = "owner")
    val owner: String,
    @ColumnInfo(name = "balance")
    val balance: Double,
    @ColumnInfo(name = "date_creation")
    val dateCreation: Long,
    @ColumnInfo(name = "date_modification")
    val dateModification: Long

)