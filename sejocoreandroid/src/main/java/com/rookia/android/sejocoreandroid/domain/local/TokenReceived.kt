package com.rookia.android.sejocoreandroid.domain.local


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

data class TokenReceived(
    val result: Int,
    val token: String? = null,
    val userId: String?
)