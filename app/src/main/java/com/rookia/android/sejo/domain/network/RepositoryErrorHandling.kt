package com.rookia.android.sejo.domain.network

import com.google.gson.Gson
import retrofit2.Response


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

interface RepositoryErrorHandling {

    fun <T> getErrorMessage(response: Response<T>): String {
        val errorBody = Gson().fromJson(response.errorBody()?.string(), ErrorBody::class.java)
        return "${response.message()} - ${errorBody.detailMessage}"
    }

}