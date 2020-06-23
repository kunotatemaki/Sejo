package com.rookia.android.sejo.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.Group
import com.rookia.android.sejo.usecases.GetGroupsUseCase

class MembersViewModel @ViewModelInject constructor(
    getGroupsUseCase: GetGroupsUseCase,
    preferencesManager: PreferencesManager
): ViewModel() {

    val group: LiveData<Group>

    init {
        val storedGroupId = preferencesManager.getLongFromPreferences(Constants.USER_DATA.LAST_USED_GROUP_TAG)
        group = getGroupsUseCase.getGroup(storedGroupId)
    }
}