package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.group.CreateGroupClient
import com.rookia.android.sejo.domain.network.group.CreateGroupServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupApi {

    @POST("create-group")
    suspend fun createGroup(@Body createGroupClient: CreateGroupClient): Response<CreateGroupServer>
}
