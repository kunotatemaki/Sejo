package com.rookia.android.sejo.framework.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.framework.repository.resultOnlyFromOneSourceInFlow
import com.rookia.android.sejo.data.persistence.PersistenceManager
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.domain.network.group.CreateGroupClient
import com.rookia.android.sejo.domain.network.toCreateGroupContact
import com.rookia.android.sejo.domain.network.toGroup
import com.rookia.android.sejo.framework.network.NetworkServiceFactory
import kotlinx.coroutines.flow.Flow
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
    private val persistenceManager: PersistenceManager
) : GroupRepository {
    override fun createGroup(
        name: String,
        fee: Int,
        owner: String,
        members: List<PhoneContact>
    ): Flow<Result<Group>> =
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
    ): Result<Group> =
        try {
            val api = networkServiceFactory.getGroupInstance()
            val groupRequest =
                CreateGroupClient(name, fee, owner, members.map { it.toCreateGroupContact() })
            val resp = api.createGroup(groupRequest)
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body()?.data?.toGroup())
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error(e.message)
        }

    override suspend fun saveGroup(group: Group) {
        persistenceManager.saveGroup(group)
    }

    override fun getGroups(): LiveData<List<Group>> =
        persistenceManager.getGroups()


}