package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.login.LoginRequestClient
import com.rookia.android.sejo.domain.network.login.LoginRequestServer
import com.rookia.android.sejo.domain.network.user.UserCreationRequestClient
import com.rookia.android.sejo.domain.network.user.UserCreationRequestServer
import com.rookia.android.sejo.domain.network.user.UserUpdateRequestClient
import com.rookia.android.sejo.domain.network.user.UserUpdateRequestServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @POST("add-user")
    suspend fun addUser(@Body userCreationRequestClient: UserCreationRequestClient): Response<UserCreationRequestServer>

    @PUT("update-user")
    suspend fun updateUser(@Body userUpdateRequestClient: UserUpdateRequestClient): Response<UserUpdateRequestServer>

//    @PUT("update-push-token")
//    suspend fun updatePushTokeen(@Body userPushTokenClient: UserPushTokenClient): Response<UserPushTokenServer>

    @POST("login")
    suspend fun login(@Body loginRequestClient: LoginRequestClient): Response<LoginRequestServer>
}
