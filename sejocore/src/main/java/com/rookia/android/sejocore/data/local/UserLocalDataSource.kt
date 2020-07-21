package com.rookia.android.sejocore.data.local


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

interface UserLocalDataSource {

    fun storeToken(token: String)

    fun getToken(): String?

}