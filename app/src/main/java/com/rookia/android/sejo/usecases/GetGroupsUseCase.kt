package com.rookia.android.sejo.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.domain.local.Group
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

class GetGroupsUseCase @Inject constructor(
    private val repository: GroupRepository
) {

    fun getGroups(userId: String): LiveData<Result<PagedList<Group>>> =
        repository.getGroups(userId)

    fun getGroup(groupId: Long): LiveData<Group> =
        repository.getGroup(groupId)

}