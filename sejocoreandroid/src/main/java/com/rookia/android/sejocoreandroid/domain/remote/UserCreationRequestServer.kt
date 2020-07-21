package com.rookia.android.sejocoreandroid.domain.remote


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

data class UserCreationRequestServer(
    val code: Int,
    val message: String,
    val data: UserCreationRequestResponse? = null
){
    data class UserCreationRequestResponse(
        val userId: String,
        val token: String
    )
}