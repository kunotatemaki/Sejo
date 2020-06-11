package com.rookia.android.sejo.domain.network.group

import com.rookia.android.sejo.domain.local.Group


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

data class RequestGroupsServer(
    val code: Int,
    val message: String,
    val data: List<Group>? = null
)