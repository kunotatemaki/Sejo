package com.rookia.android.sejo.domain.network.user


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

data class UserAdminStatusServer (
    val code: Int,
    val message: String,
    val data: UserAdminStatusResponse? = null
){
    data class UserAdminStatusResponse(
        val groupId: Long,
        val memberId: String,
        val isAdmin: Boolean
    )
}