package com.rookia.android.sejocoreandroid.domain.remote


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

data class UserPushTokenClient (
    val userId: String,
    val pushToken: String
)