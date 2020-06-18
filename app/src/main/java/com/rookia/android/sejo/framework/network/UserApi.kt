package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.ResponseWithNoData
import com.rookia.android.sejo.domain.network.user.UserCreationRequestClient
import com.rookia.android.sejo.domain.network.user.UserCreationRequestServer
import com.rookia.android.sejo.domain.network.user.UserUpdateRequestClient
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("add-user")
    suspend fun addUser(@Body userCreationRequestClient: UserCreationRequestClient): Response<UserCreationRequestServer>

    @PATCH("update-user")
    suspend fun updateUser(@Body userUpdateRequestClient: UserUpdateRequestClient): Response<ResponseWithNoData>

    @PATCH("update-last-group-selected")
    @FormUrlEncoded
    suspend fun updateLastGroupSelected(@Field("userId") userId: String, @Field("groupId") groupId: Long): Response<ResponseWithNoData>

}
