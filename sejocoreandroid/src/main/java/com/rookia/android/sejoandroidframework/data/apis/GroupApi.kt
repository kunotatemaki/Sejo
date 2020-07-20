package com.rookia.android.sejoandroidframework.data.apis

import com.rookia.android.sejoandroidframework.domain.local.CreateGroupClient
import com.rookia.android.sejoandroidframework.domain.remote.GroupsRequestServer
import com.rookia.android.sejoandroidframework.domain.remote.ResponseWithNoData
import retrofit2.Response
import retrofit2.http.*


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, July 2020
 *
 *
 */

interface GroupApi {
    @POST("create-group")
    suspend fun createGroup(@Body createGroupClient: CreateGroupClient): Response<ResponseWithNoData>

    @GET("groups/{userId}")
    suspend fun getGroups(@Path("userId") userId: String,
                          @Query("dateModification") dateModification: Long,
                          @Query("offset") offset: Int,
                          @Query("pageSize") pageSize: Int,
                          @Query("dateInclusive") dateInclusive: Boolean
    ): Response<GroupsRequestServer>
}