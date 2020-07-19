package com.rookia.android.sejoandroidframework.data.apis

import com.rookia.android.sejoandroidframework.domain.remote.LoginRequestServer
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


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

interface LoginApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("pin") pin: Int,
        @Field("userId") userId: String,
        @Field("pushToken") pushToken: String?
    ): Response<LoginRequestServer>
}