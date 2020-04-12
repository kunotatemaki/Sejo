package com.rookia.android.sejo.framework.repository

import androidx.lifecycle.LiveData
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.framework.repository.resultOnlyFromNetworkInLiveData
import com.rookia.android.sejo.data.repository.SmsCodeRepository
import com.rookia.android.sejo.domain.network.SmsCodeRequest
import com.rookia.android.sejo.framework.network.NetworkServiceFactory
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

class SmsCodeRepositoryImpl @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory
) : SmsCodeRepository {
    override fun askForSmsCode(phonePrefix: String, phoneNumber: String): LiveData<Result<Int>> =
        resultOnlyFromNetworkInLiveData {
            requestSmsFromServer(phonePrefix, phoneNumber)
        }

    suspend fun requestSmsFromServer(phonePrefix: String, phoneNumber: String): Result<Int> =
        try {
            val api = networkServiceFactory.getSmsCodeCodeInstance()
            val smsCodeRequest = SmsCodeRequest(phonePrefix, phoneNumber)
            val resp = api.requestSmsCode(smsCodeRequest)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.result)
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error("error fetching from network")
        }

}