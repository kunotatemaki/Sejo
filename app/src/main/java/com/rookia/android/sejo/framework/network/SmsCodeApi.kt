package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.SmsCodeRequestClient
import com.rookia.android.sejo.domain.network.SmsCodeRequestServer
import com.rookia.android.sejo.domain.network.SmsCodeValidationClient
import com.rookia.android.sejo.domain.network.SmsCodeValidationServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsCodeApi {

    @POST("request-sms-code")
    suspend fun requestSmsCode(@Body smsCodeRequest: SmsCodeRequestClient): Response<SmsCodeRequestServer>

    @POST("validate-sms-code")
    suspend fun validateSmsCode(@Body smsCodeValidation: SmsCodeValidationClient): Response<SmsCodeValidationServer>

}
