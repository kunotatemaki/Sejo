package com.rookia.android.sejocoreandroid.data.datasources.remote

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocoreandroid.data.network.NetworkServiceFactory
import com.rookia.android.sejocoreandroid.data.repository.RepositoryErrorHandling
import com.rookia.android.sejocoreandroid.domain.local.TokenReceived
import com.rookia.android.sejocoreandroid.domain.local.User
import com.rookia.android.sejocoreandroid.domain.remote.toTokenReceived
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

class UserRemoteDataSource @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val userLocalDataSource: UserLocalDataSource
) : RepositoryErrorHandling {
    suspend fun createUserInServer(
        phonePrefix: String,
        phoneNumber: String,
        pin: Int
    ): Result<TokenReceived> =
        try {
            val api = networkServiceFactory.getUserInstance()
            val resp = api.addUser(phonePrefix = phonePrefix, phoneNumber = phoneNumber, pin = pin)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(
                    resp.body()?.toTokenReceived().also {
                        it?.token?.let { token -> userLocalDataSource.storeToken(token) }
                        networkServiceFactory.newTokenReceived()
                    }
                )
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    suspend fun updateUserInServer(user: User): Result<Int> =
        try {
            val api = networkServiceFactory.getUserInstance()
            val resp = api.updateUser(
                pin = user.pin,
                name = user.name,
                userId = user.userId,
                pushToken = user.pushToken
            )
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.code)
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    suspend fun setLastGroupUsed(userId: String, groupId: Long): Result<Int> =
        try {
            val api = networkServiceFactory.getUserInstance()

            val resp = api.updateLastGroupSelected(userId, groupId)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.code)
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }


}