package com.rookia.android.sejoandroidframework.domain.local


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

class CreateGroupClient (
    val name: String,
    val fee: Int,
    val owner: String,
    val members: List<Contact>
){
    data class Contact(
        val numberId: String
    )
}