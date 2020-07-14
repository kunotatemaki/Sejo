package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.ResponseWithNoData
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationServer
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
