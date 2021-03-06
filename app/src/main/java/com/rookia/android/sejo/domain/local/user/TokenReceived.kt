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

data class TokenReceived(
    val result: Int,
    val token: String? = null,
    val userId: String?
)