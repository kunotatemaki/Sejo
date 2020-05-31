package com.rookia.android.sejo.framework.repository

import androidx.annotation.VisibleForTesting
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.framework.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.data.repository.UserRepository
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.domain.local.user.User
import com.rookia.android.sejo.domain.network.login.LoginRequestClient
import com.rookia.android.sejo.domain.network.toTokenReceived
import com.rookia.android.sejo.domain.network.toUserUpdateRequestClient
import com.rookia.android.sejo.domain.network.user.UserCreationRequestClient
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
) : UserRepository {
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
            val userRequest = UserCreationRequestClient(phonePrefix, phoneNumber, pin)
            val resp = api.addUser(userRequest)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.toTokenReceived())
            } else {
                Result.error(resp.message())
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
            val updateUserValidation = user.toUserUpdateRequestClient()
            val resp = api.updateUser(updateUserValidation)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.code)
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    override fun login(
        userId: String,
        pin: Int,
        pushToken: String?
    ): Flow<Result<TokenReceived>> =
        resultOnlyFromOneSourceInFlow {
            loginInServer(userId, pin, pushToken)
        }

    @VisibleForTesting
    suspend fun loginInServer(
        userId: String,
        pin: Int,
        pushToken: String?
    ): Result<TokenReceived> =
        try {
            val api = networkServiceFactory.getUserInstance()
            val loginRequest = LoginRequestClient(pin = pin, userId = userId, pushToken = pushToken)
            val resp = api.login(loginRequest)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.toTokenReceived())
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    override fun storeToken(token: String) {
        preferencesManager.setStringIntoPreferences(Constants.USER_TOKEN_TAG, token)
    }

}