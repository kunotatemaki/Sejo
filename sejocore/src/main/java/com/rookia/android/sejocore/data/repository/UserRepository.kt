package com.rookia.android.sejocore.data.repository

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.kotlinutils.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejocore.data.local.UserLocalDataSource
import com.rookia.android.sejocore.data.remote.UserRemoteDataSource
import com.rookia.android.sejocore.domain.local.TokenReceived
import com.rookia.android.sejocore.domain.local.User
import kotlinx.coroutines.flow.Flow


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

class UserRepository constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
){

    fun createUser(
        phonePrefix: String,
        phoneNumber: String,
        pin: Int
    ): Flow<Result<TokenReceived>> =
        resultOnlyFromOneSourceInFlow {
            userRemoteDataSource.createUserInServer(phonePrefix, phoneNumber, pin)
        }

    fun updateUser(user: User): Flow<Result<Int>> =
        resultOnlyFromOneSourceInFlow {
            userRemoteDataSource.updateUserInServer(user)
        }

    fun storeToken(token: String){
        userLocalDataSource.storeToken(token)
    }

    fun updateLastGroupSelected(userId: String, groupId: Long): Flow<Result<Int>> =
        resultOnlyFromOneSourceInFlow {
            userRemoteDataSource.setLastGroupUsed(userId, groupId)
        }


}