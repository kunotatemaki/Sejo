package com.rookia.android.sejo.domain.network.group


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

data class CreateGroupClient (
    val name: String,
    val fee: Int,
    val owner: String,
    val members: List<Contact>
){
    data class Contact(
        val numberId: String
    )
}