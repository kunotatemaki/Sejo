package com.rookia.android.sejo.framework.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

open class NetworkServiceFactory {

    companion object {
//        const val ROOKIA_EXPENSES_SERVER_BASE_URL = "http://10.0.2.2:8080"
        const val ROOKIA_EXPENSES_SERVER_BASE_URL = "http://192.168.1.34:8080"

    }

    @Volatile
    private var smsCodeCodeInstance: SmsCodeApi? = null

    open fun getSmsCodeCodeInstance(): SmsCodeApi =
        smsCodeCodeInstance ?: buildSmsCodeNetworkService().also { smsCodeCodeInstance = it }

    private fun buildSmsCodeNetworkService(): SmsCodeApi = Retrofit.Builder()
        .baseUrl(ROOKIA_EXPENSES_SERVER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(SmsCodeApi::class.java)


}
 