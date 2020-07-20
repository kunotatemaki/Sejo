package com.rookia.android.sejoandroidframework.domain.remote


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

class GroupsRequestServer(
    val code: Int,
    val message: String,
    val data: List<GroupServer>? = null
) {
    data class GroupServer(
        val groupId: Long,
        val name: String,
        val fee: Float,
        val owner: String,
        val dateCreation: String? = null,
        val balance: Double,
        var members: List<GroupContactServer>,
        val dateModification: String? = null
    )

    data class GroupContactServer(
        val numberId: String,
        val isAdmin: Boolean?,
        val memberStatus: Int?
    )
}