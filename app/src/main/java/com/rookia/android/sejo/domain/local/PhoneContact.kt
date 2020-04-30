package com.rookia.android.sejo.domain.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


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

@Parcelize
data class PhoneContact (
    val id: String,
    val phoneNumber: String,
    val phoneNumberNormalized: String,
    val name: String,
    val photoUrl: String?,
    var isAdmin: Boolean = false
) : Parcelable