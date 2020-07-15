package com.rookia.android.sejo.domain.local

import com.rookia.android.sejo.framework.utils.DateUtils


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

data class Group(
    val groupId: Long,
    val name: String,
    val fee: Float,
    val owner: String,
    val dateCreation: String? = null,
    val balance: Double,
    var members: List<GroupContact>,
    val dateModification: String? = null
){
    data class GroupContact(
        val numberId: String,
        val isAdmin: Boolean?,
        val memberStatus: Int?
    )

    fun getDateModificationAsUTCTimestamp(dateUtils: DateUtils): Long =
        dateUtils.convertZuluTimeToUTCTimestamp(dateModification)

    fun getDateCreationAsUTCTimestamp(dateUtils: DateUtils): Long =
        dateUtils.convertZuluTimeToUTCTimestamp(dateCreation)
}
