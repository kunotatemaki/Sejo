package com.rookia.android.sejo.domain.network


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

enum class LoginResponse constructor(val code: Int){
    OK(0),
    ERROR(1),
    NOT_AUTHORIZED(2),
    NO_USER(3);
}