package com.rookia.android.sejo.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.usecases.GetGroupsUseCase


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

class SelectedGroupViewModel @ViewModelInject constructor(
    getGroupsUseCase: GetGroupsUseCase,
    preferencesManager: PreferencesManager
) : ViewModel() {

    val group: LiveData<Group>

    init {
        val storedGroupId = preferencesManager.getLongFromPreferences(Constants.USER_DATA.LAST_USED_GROUP_TAG)
        group = getGroupsUseCase.getGroup(storedGroupId)
    }

}