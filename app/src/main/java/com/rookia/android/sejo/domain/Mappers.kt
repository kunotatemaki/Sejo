package com.rookia.android.sejo.domain

import com.rookia.android.sejo.domain.local.PhoneContactParcelable
import com.rookia.android.sejocoreandroid.domain.local.PhoneContact


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

fun PhoneContact.toPhoneContactParcelable(): PhoneContactParcelable =
    PhoneContactParcelable(
        id = id,
        phoneNumber =  phoneNumber,
        phoneNumberNormalized = phoneNumberNormalized,
        name = name,
        photoUrl = photoUrl,
        isAdmin = isAdmin
    )

fun PhoneContactParcelable.toPhoneContact(): PhoneContact =
    PhoneContact(
        id = id,
        phoneNumber =  phoneNumber,
        phoneNumberNormalized = phoneNumberNormalized,
        name = name,
        photoUrl = photoUrl,
        isAdmin = isAdmin
    )