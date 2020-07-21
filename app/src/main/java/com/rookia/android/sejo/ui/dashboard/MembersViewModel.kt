package com.rookia.android.sejo.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.usecases.GetGroupsUseCase
import com.rookia.android.sejocore.domain.local.Group

class MembersViewModel @ViewModelInject constructor(
    getGroupsUseCase: GetGroupsUseCase,
    preferencesManager: PreferencesManager
): ViewModel() {

    val group: LiveData<Group>

    init {
        val storedGroupId = preferencesManager.getLongFromPreferences(Constants.UserData.LAST_USED_GROUP_TAG)
        group = getGroupsUseCase.getGroup(storedGroupId).asLiveData()
    }
}