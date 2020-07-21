package com.rookia.android.sejocoreandroid.data.datasources.remote

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocoreandroid.data.network.NetworkServiceFactory
import com.rookia.android.sejocoreandroid.data.repository.RepositoryErrorHandling
import com.rookia.android.sejocoreandroid.domain.local.TokenReceived
import com.rookia.android.sejocoreandroid.domain.remote.toTokenReceived
import com.rookia.android.sejocoreandroid.utils.TokenUtils
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

class LoginRemoteDataSource @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val tokenUtils: TokenUtils
) : RepositoryErrorHandling {

    suspend fun loginInServer(
        userId: String,
        pin: Int,
        pushToken: String?
    ): Result<TokenReceived> =
        try {
            val api = networkServiceFactory.getLoginInstance()
            val resp = api.login(pin, userId, pushToken)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(
                    resp.body()?.toTokenReceived().also {
                        it?.token?.let { token -> tokenUtils.storeToken(token) }
                        networkServiceFactory.newTokenReceived()
                    }
                )
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

}