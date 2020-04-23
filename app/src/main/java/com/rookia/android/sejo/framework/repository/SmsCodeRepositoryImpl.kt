package com.rookia.android.sejo.framework.repository

import androidx.annotation.VisibleForTesting
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.framework.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejo.data.repository.SmsCodeRepository
import com.rookia.android.sejo.domain.local.smscode.SmsCodeValidation
import com.rookia.android.sejo.domain.network.smscode.SmsCodeRequestClient
import com.rookia.android.sejo.domain.network.smscode.SmsCodeValidationClient
import com.rookia.android.sejo.domain.network.toSmsCodeValidation
import com.rookia.android.sejo.framework.network.NetworkServiceFactory
import kotlinx.coroutines.flow.Flow
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
    override fun askForSmsCode(phonePrefix: String, phoneNumber: String): Flow<Result<Int>> =
        resultOnlyFromOneSourceInFlow {
            requestSmsFromServer(phonePrefix, phoneNumber)
        }

    @VisibleForTesting
    suspend fun requestSmsFromServer(phonePrefix: String, phoneNumber: String): Result<Int> =
        try {
            val api = networkServiceFactory.getSmsCodeCodeInstance()
            val smsCodeRequest = SmsCodeRequestClient(phonePrefix, phoneNumber)
            val resp = api.requestSmsCode(smsCodeRequest)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.result)
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    override fun validateSmsCode(
        phonePrefix: String,
        phoneNumber: String,
        smsCode: String
    ): Flow<Result<SmsCodeValidation>> =
        resultOnlyFromOneSourceInFlow {
            validateSmsFromServer(phonePrefix, phoneNumber, smsCode)
        }


    @VisibleForTesting
    suspend fun validateSmsFromServer(
        phonePrefix: String,
        phoneNumber: String,
        smsCode: String
    ): Result<SmsCodeValidation> =
        try {
            val api = networkServiceFactory.getSmsCodeCodeInstance()
            val smsCodeValidation =
                SmsCodeValidationClient(
                    phonePrefix,
                    phoneNumber,
                    smsCode
                )
            val resp = api.validateSmsCode(smsCodeValidation)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.toSmsCodeValidation())
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

}