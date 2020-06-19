package com.rookia.android.sejo.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.di.modules.ProvidesModule
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.usecases.GetGroupsUseCase
import com.rookia.android.sejo.usecases.SetLastGroupSelectedUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


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

class GroupsViewModel @ViewModelInject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val preferencesManager: PreferencesManager,
    private val selectedGroupUseCase: SetLastGroupSelectedUseCase,
    @ProvidesModule.IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val groups: MediatorLiveData<Result<PagedList<Group>>> = MediatorLiveData()

    val group: LiveData<Group>
    private val _singleGroupQuery: MutableLiveData<Long> = MutableLiveData()

    init {
        val storedGroupId = preferencesManager.getLongFromPreferences(Constants.LAST_USED_GROUP_TAG)
        _singleGroupQuery.value = storedGroupId
        group = _singleGroupQuery.switchMap { groupId ->
            getGroupsUseCase.getGroup(groupId)
        }
    }

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

    fun setSelectedGroup(groupId: Long) {
        _singleGroupQuery.value = groupId
        preferencesManager.getStringFromPreferences(Constants.USER_ID_TAG)?.let { userId ->
            viewModelScope.launch(dispatcher) {
                selectedGroupUseCase.updateUser(userId, groupId).collect {
                    Timber.d("")
                }
            }

        }
    }

}