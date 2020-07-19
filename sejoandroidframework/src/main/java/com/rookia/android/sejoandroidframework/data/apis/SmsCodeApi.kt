package com.rookia.android.sejoandroidframework.data.apis

import com.rookia.android.sejoandroidframework.domain.remote.ResponseWithNoData
import com.rookia.android.sejoandroidframework.domain.remote.SmsCodeValidationServer
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

interface SmsCodeApi {

    @FormUrlEncoded
    @POST("request-sms-code")
    suspend fun requestSmsCode(
        @Field("phonePrefix") phonePrefix: String,
        @Field("phoneNumber") phoneNumber: String
    ): Response<ResponseWithNoData>

    @FormUrlEncoded
    @POST("validate-sms-code")
    suspend fun validateSmsCode(
        @Field("phonePrefix") phonePrefix: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("smsCode") smsCode: String
    ): Response<SmsCodeValidationServer>

}