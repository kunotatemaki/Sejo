package com.rookia.android.sejocore.domain.local


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

data class PhoneContact (
    val id: String,
    val phoneNumber: String,
    val phoneNumberNormalized: String,
    val name: String,
    val photoUrl: String?,
    var isAdmin: Boolean = false
)