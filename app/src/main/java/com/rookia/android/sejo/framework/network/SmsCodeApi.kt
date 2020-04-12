package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.SmsCodeRequest
import com.rookia.android.sejo.domain.network.SmsCodeRequestResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsCodeApi {

    @POST("request-sms-code")
    suspend fun requestSmsCode(@Body smsCodeRequest: SmsCodeRequest): Response<SmsCodeRequestResponse>

}
