package com.rookia.android.sejoandroidframework.data.datasources

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejoandroidframework.data.network.NetworkServiceFactory
import com.rookia.android.sejoandroidframework.data.repository.RepositoryErrorHandling
import com.rookia.android.sejoandroidframework.domain.remote.toTokenReceived
import com.rookia.android.sejoandroidframework.utils.TokenUtils
import com.rookia.android.sejocore.data.remote.LoginRemoteDataSource
import com.rookia.android.sejocore.domain.local.TokenReceived
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

class LoginRemoteDataSourceImpl @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val tokenUtils: TokenUtils
) : LoginRemoteDataSource, RepositoryErrorHandling {

    override suspend fun loginInServer(
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