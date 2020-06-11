package com.rookia.android.sejo.ui.dashboard

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.usecases.GetGroupsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named


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
    @Named("IO") private val dispatcher: CoroutineDispatcher,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val groups: MediatorLiveData<Result<List<Group>>> = MediatorLiveData()

    fun checkForNewGroups() {
        preferencesManager.getStringFromPreferences(Constants.USER_ID_TAG)?.let { userId->
            val lastChecked = preferencesManager.getLongFromPreferences(Constants.LAST_CHECKED_TIMESTAMP, 0L)
            val groupsInternal = getGroupsUseCase.getGroups(userId, lastChecked).asLiveData(dispatcher)
            groups.addSource(groupsInternal){
                groups.value = it
                if(it.status != Result.Status.LOADING){
                    groups.removeSource(groupsInternal)
                }
            }
        }
    }



}