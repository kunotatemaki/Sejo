package com.rookia.android.sejo.ui.dashboard

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.usecases.GetGroupsUseCase
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, June 2020
 *
 *
 */

class GeneralViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val groups: MediatorLiveData<Result<PagedList<Group>>> = MediatorLiveData()

    fun loadGroups() {
        preferencesManager.getStringFromPreferences(Constants.USER_ID_TAG)?.let { userId ->
            val groupsInternal =
                getGroupsUseCase.getGroups(userId)
            groups.addSource(groupsInternal) {
                groups.value = it
                if (it.status != Result.Status.LOADING) {
                    groups.removeSource(groupsInternal)
                }
            }
        }
    }


}