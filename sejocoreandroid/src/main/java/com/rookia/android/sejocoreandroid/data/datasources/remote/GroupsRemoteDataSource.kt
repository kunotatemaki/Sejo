package com.rookia.android.sejocoreandroid.data.datasources.remote

import com.rookia.android.androidutils.utils.DateUtils
import com.rookia.android.kotlinutils.domain.vo.Result
import com.rookia.android.sejocoreandroid.data.datasources.local.CacheSanity
import com.rookia.android.sejocoreandroid.data.datasources.local.GroupsLocalDataSource
import com.rookia.android.sejocoreandroid.data.network.NetworkServiceFactory
import com.rookia.android.sejocoreandroid.domain.local.CreateGroupClient
import com.rookia.android.sejocoreandroid.domain.local.Group
import com.rookia.android.sejocoreandroid.domain.local.PhoneContact
import com.rookia.android.sejocoreandroid.domain.remote.toCreateGroupContact
import com.rookia.android.sejocoreandroid.domain.remote.toGroup
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

class GroupsRemoteDataSource @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val groupsLocalDataSource: GroupsLocalDataSource,
    private val dateUtils: DateUtils
)  {

    companion object {
        private const val NUMBER_OF_GROUPS_PER_PAGE_QUERIED = 100
    }

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


    suspend fun getGroupsFromServer(userId: String): Result<List<Group>> {
        val requestTimestamp = System.currentTimeMillis()
        val listOfGroups = mutableListOf<Group>()
        val api = networkServiceFactory.getGroupInstance()
        var offset = 0
        var dateInclusive = false

        var needToRequest = true
        while (needToRequest) {
            val dateModification = groupsLocalDataSource.getLastRequestedTime()
            try {

                val resp = api.getGroups(
                    userId,
                    dateModification,
                    offset,
                    NUMBER_OF_GROUPS_PER_PAGE_QUERIED,
                    dateInclusive
                )

                if (resp.isSuccessful && resp.body() != null) {

                    val listOfGroupsReturned =
                        resp.body()?.data?.map { it.toGroup(dateUtils) } ?: listOf()
                    val lastCheckedDate =
                        listOfGroupsReturned.maxBy { it.dateModification }?.dateModification ?: 0L

                    groupsLocalDataSource.saveLastRequestedTime(lastCheckedDate)

                    listOfGroups.addAll(listOfGroupsReturned)
                    if (listOfGroupsReturned.size < NUMBER_OF_GROUPS_PER_PAGE_QUERIED) {
                        needToRequest = false
                        groupsLocalDataSource.saveLastRequestedTime(requestTimestamp)
                    } else {
                        offset = listOfGroups.count { it.dateModification == lastCheckedDate }
                        dateInclusive = true
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

}