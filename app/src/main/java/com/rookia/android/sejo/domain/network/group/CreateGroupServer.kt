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
    val data: CreateGroupResponse? = null
) {

    data class CreateGroupResponse(
        val groupId: Int,
        val name: String,
        val fee: Int,
        val owner: String,
        val date: Long,
        val balance: Double,
        var members: List<Member> = listOf()
    )

    data class Member(
        val numberId: String,
        val memberStatus: Int?
    )
}