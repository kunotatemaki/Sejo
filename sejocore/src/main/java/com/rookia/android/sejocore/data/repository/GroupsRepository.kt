package com.rookia.android.sejocore.data.repository

import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.kotlinutils.repository.resultFromPersistenceAndNetworkInFlow
import com.rookia.android.kotlinutils.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.kotlinutils.utils.RateLimiter
import com.rookia.android.sejocore.data.local.CacheSanity
import com.rookia.android.sejocore.data.local.GroupsLocalDataSource
import com.rookia.android.sejocore.data.remote.GroupsRemoteDataSource
import com.rookia.android.sejocore.domain.local.Group
import com.rookia.android.sejocore.domain.local.PhoneContact
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit


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
 
class GroupsRepository constructor(
    private val groupsRemoteDataSource: GroupsRemoteDataSource,
    private val groupsLocalDataSource: GroupsLocalDataSource,
    private val rateLimiter: RateLimiter
){

    fun createGroup(
        name: String,
        fee: Int,
        owner: String,
        members: List<PhoneContact>
    ): Flow<Result<Unit>> =
        resultOnlyFromOneSourceInFlow {
            groupsRemoteDataSource.createGroupInServer(
                name,
                fee,
                owner,
                members
            )
        }

    suspend fun saveGroups(groups: List<Group>) {
        groupsLocalDataSource.saveGroups(groups)
    }

    fun getGroups(userId: String): Flow<Result<List<Group>>> {
        val lastCheckedDate = groupsLocalDataSource.getLastRequestedTime()

        return resultFromPersistenceAndNetworkInFlow(
            persistedDataQuery = { groupsLocalDataSource.getGroups() },
            networkCall = { groupsRemoteDataSource.getGroupsFromServer(userId) },
            persistCallResult = { listOfGroups ->
                listOfGroups?.let {
                    groupsLocalDataSource.saveGroups(it)
                }
            },
            isThePersistedInfoOutdated = {
                rateLimiter.expired(
                    lastCheckedDate,
                    5,
                    TimeUnit.SECONDS
                ) || CacheSanity.groupsCacheDirty
            }
        )
    }

    fun getGroup(groupId: Long): Flow<Group> = groupsLocalDataSource.getGroup(groupId)
}