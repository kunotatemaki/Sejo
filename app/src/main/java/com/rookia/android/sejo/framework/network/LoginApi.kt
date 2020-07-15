package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.login.LoginRequestServer
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("pin") pin: Int,
        @Field("userId") userId: String,
        @Field("pushToken") pushToken: String?
    ): Response<LoginRequestServer>

}