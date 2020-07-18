package com.rookia.android.sejo.framework.repository

import androidx.annotation.VisibleForTesting
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.framework.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.data.repository.UserRepository
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.domain.local.user.User
import com.rookia.android.sejo.domain.network.RepositoryErrorHandling
import com.rookia.android.sejo.domain.network.toTokenReceived
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

class UserRepositoryImpl @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val preferencesManager: PreferencesManager
) : UserRepository, RepositoryErrorHandling {
    override fun createUser(
        phonePrefix: String,
        phoneNumber: String,
        pin: Int
    ): Flow<Result<TokenReceived>> =
        resultOnlyFromOneSourceInFlow {
            createUserInServer(phonePrefix, phoneNumber, pin)
        }

    @VisibleForTesting
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
                        it?.token?.let { token -> storeToken(token) }
                        networkServiceFactory.newTokenReceived()
                    }
                )
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    override fun updateUser(user: User): Flow<Result<Int>> =
        resultOnlyFromOneSourceInFlow {
            updateUserInServer(user)
        }


    @VisibleForTesting
    suspend fun updateUserInServer(user: User): Result<Int> =
        try {
            val api = networkServiceFactory.getUserInstance()
            val resp = api.updateUser(pin = user.pin, name = user.name, userId = user.userId, pushToken = user.pushToken)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.code)
            } else {
                Result.error(getErrorMessage(resp))
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    private fun storeToken(token: String) {
        preferencesManager.setStringIntoPreferences(Constants.UserData.TOKEN_TAG, token)
    }

    override fun updateLastGroupSelected(userId: String, groupId: Long): Flow<Result<Int>> =
        resultOnlyFromOneSourceInFlow {
            setLastGroupUsed(userId, groupId)
        }


    @VisibleForTesting
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