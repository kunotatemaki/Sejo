package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.ResponseWithNoData
import com.rookia.android.sejo.domain.network.user.UserCreationRequestClient
import com.rookia.android.sejo.domain.network.user.UserCreationRequestServer
import com.rookia.android.sejo.domain.network.user.UserUpdateRequestClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @POST("add-user")
    suspend fun addUser(@Body userCreationRequestClient: UserCreationRequestClient): Response<UserCreationRequestServer>

    @PUT("update-user")
    suspend fun updateUser(@Body userUpdateRequestClient: UserUpdateRequestClient): Response<ResponseWithNoData>

}
