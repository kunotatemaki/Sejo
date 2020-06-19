package com.rookia.android.sejo.framework.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.framework.repository.resultFromPersistenceAndNetworkInLivePagedList
import com.rookia.android.androidutils.framework.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.androidutils.utils.RateLimiter
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.data.CacheSanity
import com.rookia.android.sejo.data.persistence.PersistenceManager
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.domain.network.group.CreateGroupClient
import com.rookia.android.sejo.domain.network.toCreateGroupContact
import com.rookia.android.sejo.framework.network.NetworkServiceFactory
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, May 2020
 *
 *
 */

class GroupRepositoryImpl @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val persistenceManager: PersistenceManager,
    private val rateLimiter: RateLimiter,
    private val preferencesManager: PreferencesManager
) : GroupRepository {
    override fun createGroup(
        name: String,
        fee: Int,
        owner: String,
        members: List<PhoneContact>
    ): Flow<Result<Unit>> =
        resultOnlyFromOneSourceInFlow {
            createGroupInServer(
                name,
                fee,
                owner,
                members
            )
        }

    @VisibleForTesting
    suspend fun createGroupInServer(
        name: String,
        fee: Int,
        owner: String,
        members: List<PhoneContact>
    ): Result<Unit> =
        try {
            val api = networkServiceFactory.getGroupInstance()
            val groupRequest =
                CreateGroupClient(name, fee, owner, members.map { it.toCreateGroupContact() })
            val resp = api.createGroup(groupRequest)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(Unit)
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    override suspend fun saveGroups(groups: List<Group>) {
        persistenceManager.saveGroups(groups)
    }

    override fun getGroups(userId: String): LiveData<Result<PagedList<Group>>> {
        val lastCheckedDate =
            preferencesManager.getLongFromPreferences(Constants.GROUPS.LAST_CHECKED_TIMESTAMP, 0L)
        return resultFromPersistenceAndNetworkInLivePagedList(
            persistedDataQuery = { persistenceManager.getGroups() },
            networkCall = { getGroupsFromServer(userId) },
            persistCallResult = { listOfGroups ->
                listOfGroups?.let {
                    persistenceManager.saveGroups(it)
                }
            },
            isThePersistedInfoOutdated = {
                rateLimiter.expired(
                    lastCheckedDate,
                    5,
                    TimeUnit.MINUTES
                ) || CacheSanity.groupsCacheDirty
            }
        )
    }

    private suspend fun getGroupsFromServer(userId: String): Result<List<Group>> {


        val requestTimestamp = System.currentTimeMillis()
        val listOfGroups = mutableListOf<Group>()
        val api = networkServiceFactory.getGroupInstance()

        var needToRequest = true
        while (needToRequest) {
            val dateModification = getLastRequestedTime()
            try {

                val resp = api.getGroups(
                    userId,
                    dateModification,
                    Constants.GROUPS.NUMBER_OF_GROUPS_PER_PAGE_QUERIED
                )

                if (resp.isSuccessful && resp.body() != null) {

                    val lastCheckedDate =
                        resp.body()?.data?.maxBy { it.dateModification ?: 0L }?.dateModification
                            ?: 0L

                    if (dateModification < lastCheckedDate) {
                        saveLastRequestedTime(lastCheckedDate)
                    }

                    val listOfGroupsReturned = resp.body()?.data ?: listOf()
                    listOfGroups.addAll(listOfGroupsReturned)
                    if (listOfGroupsReturned.size < Constants.GROUPS.NUMBER_OF_GROUPS_PER_PAGE_QUERIED) {
                        needToRequest = false
                        saveLastRequestedTime(requestTimestamp)
                    }

                } else {
                    return Result.error(resp.message(), listOfGroups)
                }

            } catch (e: Exception) {
                return Result.error(e.message, listOfGroups)
            }

        }

        CacheSanity.groupsCacheDirty = false
        return Result.success(listOfGroups)

    }

    private fun saveLastRequestedTime(timestamp: Long) {
        preferencesManager.setLongIntoPreferences(
            Constants.GROUPS.LAST_CHECKED_TIMESTAMP,
            timestamp
        )
    }

    private fun getLastRequestedTime(): Long =
        preferencesManager.getLongFromPreferences(Constants.GROUPS.LAST_CHECKED_TIMESTAMP, 0L)

    override fun getGroup(groupId: Long): LiveData<Group> =
        persistenceManager.getGroup(groupId)

//    override fun getGroupWithMembers(groupId: Long): LiveData<GroupWithMembers> =
//        persistenceManager.getGroupWithMembers(groupId)

}