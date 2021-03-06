package com.rookia.android.sejo.domain.local.user


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

data class User(
    val userId: String,
    val phonePrefix: String,
    val phoneNumber: String,
    val name: String,
    var pin: Int? = null,
    var pushToken: String? = null
)