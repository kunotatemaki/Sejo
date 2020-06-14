package com.rookia.android.sejo.framework.network

import com.rookia.android.sejo.domain.network.ResponseWithNoData
import com.rookia.android.sejo.domain.network.group.CreateGroupClient
import com.rookia.android.sejo.domain.network.group.RequestGroupsServer
import retrofit2.Response
import retrofit2.http.*

interface GroupApi {

    @POST("create-group")
    suspend fun createGroup(@Body createGroupClient: CreateGroupClient): Response<ResponseWithNoData>

    @GET("groups/{userId}")
    suspend fun getGroups(@Path("userId") userId: String,
                          @Query("dateModification") dateModification: Long,
                          @Query("pageSize") pageSize: Int
                          ): Response<RequestGroupsServer>
}
