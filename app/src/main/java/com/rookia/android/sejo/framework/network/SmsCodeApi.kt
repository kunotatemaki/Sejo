package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.ResponseWithNoData
import com.rookia.android.sejo.domain.network.smscode.SmsCodeRequestClient
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationClient
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsCodeApi {

    @POST("request-sms-code")
    suspend fun requestSmsCode(@Body smsCodeRequest: SmsCodeRequestClient): Response<ResponseWithNoData>

    @POST("validate-sms-code")
    suspend fun validateSmsCode(@Body smsCodeValidation: SmsCodeValidationClient): Response<SmsCodeValidationServer>

}
