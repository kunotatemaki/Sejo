package com.rookia.android.sejocoreandroid.data.apis

import com.rookia.android.sejocoreandroid.domain.remote.ResponseWithNoData
import com.rookia.android.sejocoreandroid.domain.remote.UserCreationRequestServer
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PATCH
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

interface UserApi {

    @FormUrlEncoded
    @POST("add-user")
    suspend fun addUser(
        @Field("phonePrefix") phonePrefix: String,
        @Field("phoneNumber") phoneNumber: String?,
        @Field("pin") pin: Int
    ): Response<UserCreationRequestServer>

    @FormUrlEncoded
    @PATCH("update-user")
    suspend fun updateUser(
        @Field("pin") pin: Int?,
        @Field("name") name: String?,
        @Field("userId") userId: String,
        @Field("pushToken") pushToken: String?
    ): Response<ResponseWithNoData>

    @FormUrlEncoded
    @PATCH("update-last-group-selected")
    suspend fun updateLastGroupSelected(@Field("userId") userId: String, @Field("groupId") groupId: Long): Response<ResponseWithNoData>

}