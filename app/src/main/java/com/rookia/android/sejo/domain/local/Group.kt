package com.rookia.android.sejo.domain.local


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

data class Group (
    val grouoId: Int,
    val name: String,
    val fee: Int,
    val owner: String,
    val balance: Double,
    val members: List<Member>
){
    data class Member(
        val numberId: String,
        val memberStatus: Int
    )
}
