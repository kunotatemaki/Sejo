package com.rookia.android.sejocoreandroid.domain.remote


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

data class LoginRequestServer(
    val code: Int,
    val message: String,
    val data: LoginRequestResponse? = null

){
    data class LoginRequestResponse (
        val token: String? = null
    )
}