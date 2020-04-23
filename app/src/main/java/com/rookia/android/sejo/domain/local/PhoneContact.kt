package com.rookia.android.sejo.domain.local


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

data class PhoneContact (
    val id: String,
    val phone: String,
    val name: String,
    val photoUrl: String?
)