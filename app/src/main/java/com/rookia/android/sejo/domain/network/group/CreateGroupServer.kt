package com.rookia.android.sejo.domain.network.group


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */

data class CreateGroupServer(
    val code: Int,
    val message: String,
    val data: CreateGroupResponses? = null
) {

    data class CreateGroupResponses(
        val groupId: Int,
        val name: String,
        val fee: Int,
        val nAdmins: Int,
        val date: Long,
        var members: List<String> = listOf(),
        var membersInApp: List<String> = listOf()
    )
}