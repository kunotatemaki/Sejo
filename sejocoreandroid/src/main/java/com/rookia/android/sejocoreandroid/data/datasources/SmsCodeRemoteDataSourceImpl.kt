package com.rookia.android.sejocoreandroid.data.datasources

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocore.data.remote.SmsCodeRemoteDataSource
import com.rookia.android.sejocore.domain.local.SmsCodeValidation
import com.rookia.android.sejocoreandroid.data.network.NetworkServiceFactory
import com.rookia.android.sejocoreandroid.data.repository.RepositoryErrorHandling
import com.rookia.android.sejocoreandroid.domain.remote.toSmsCodeValidation
import javax.inject.Inject

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

class SmsCodeRemoteDataSourceImpl @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory
): SmsCodeRemoteDataSource, RepositoryErrorHandling {
    override suspend fun requestSmsFromServer(phonePrefix: String, phoneNumber: String): Result<Int> =
        try {
            val api = networkServiceFactory.getSmsCodeCodeInstance()
            val resp = api.requestSmsCode(phonePrefix, phoneNumber)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.code)
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    override suspend fun validateSmsFromServer(
        phonePrefix: String,
        phoneNumber: String,
        smsCode: String
    ): Result<SmsCodeValidation> =
        try {
            val api = networkServiceFactory.getSmsCodeCodeInstance()
            val resp = api.validateSmsCode(phonePrefix, phoneNumber, smsCode)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.toSmsCodeValidation())
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }
}