package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.ResponseWithNoData
import com.rookia.android.sejo.domain.network.group.CreateGroupClient
import com.rookia.android.sejo.domain.network.group.RequestGroupsServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupApi {

    @POST("create-group")
    suspend fun createGroup(@Body createGroupClient: CreateGroupClient): Response<ResponseWithNoData>

    @GET("groups/{userId}/{dateModification}")
    suspend fun getGroups(@Path("userId") userId: String, @Path("dateModification") dateModification: Long): Response<RequestGroupsServer>
}
