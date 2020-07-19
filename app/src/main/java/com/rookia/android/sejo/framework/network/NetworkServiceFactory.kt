package com.rookia.android.sejo.framework.network

import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */

open class NetworkServiceFactory @Inject constructor(private val preferencesManager: PreferencesManager) {

    companion object {
        const val ROOKIA_EXPENSES_SERVER_BASE_URL = "http://10.0.2.2:8080"
//        const val ROOKIA_EXPENSES_SERVER_BASE_URL = "http://192.168.1.53:8080"

    }

    @Volatile
    private var userInstance: UserApi? = null

    @Volatile
    private var groupInstance: GroupApi? = null

    fun getUserInstance(): UserApi {
        val bearer = preferencesManager.getStringFromPreferences(Constants.UserData.TOKEN_TAG)

        return userInstance ?: buildUserNetworkService(bearer).also { userApi ->
            bearer?.let { userInstance = userApi }
        }
    }

    fun getGroupInstance(): GroupApi {
        val bearer = preferencesManager.getStringFromPreferences(Constants.UserData.TOKEN_TAG)

        return groupInstance ?: buildGroupNetworkService(bearer).also { groupApi ->
            bearer?.let { groupInstance = groupApi }
        }
    }

    fun newTokenReceived() {
        userInstance = null
        groupInstance = null
    }

    private fun buildUserNetworkService(bearer: String?): UserApi =
        Retrofit.Builder()
            .baseUrl(ROOKIA_EXPENSES_SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptorForAuthentication(bearer))
            .build().create(UserApi::class.java)


    private fun buildGroupNetworkService(bearer: String?): GroupApi =
        Retrofit.Builder()
            .baseUrl(ROOKIA_EXPENSES_SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptorForAuthentication(bearer))
            .build().create(GroupApi::class.java)


    private fun getInterceptorForAuthentication(bearer: String?): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        httpClient.addInterceptor(object : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request.Builder = chain.request().newBuilder()
                bearer?.let {
                    request.addHeader("Authorization", bearer)
                }
                return chain.proceed(request.build())
            }
        })
        return httpClient.build()
    }


}
 