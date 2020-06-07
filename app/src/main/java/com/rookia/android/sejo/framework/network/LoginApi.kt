package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.login.LoginRequestClient
import com.rookia.android.sejo.domain.network.login.LoginRequestServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    suspend fun login(@Body loginRequestClient: LoginRequestClient): Response<LoginRequestServer>

}
