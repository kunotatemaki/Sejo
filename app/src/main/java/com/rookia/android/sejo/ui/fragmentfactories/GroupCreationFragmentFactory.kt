package com.rookia.android.sejo.ui.fragmentfactories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.framework.utils.PermissionManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.androidutils.utils.DeviceUtils
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

class GroupCreationFragmentFactory @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val permissionManager: PermissionManager,
    private val preferencesManager: PreferencesManager,
    private val resourcesManager: ResourcesManager,
    private val deviceUtils: DeviceUtils
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
//            GroupCreationMainInfoFragment::class.java -> GroupCreationMainInfoFragment(resourcesManager)
//            GroupCreationMembersFragment::class.java -> GroupCreationMembersFragment(viewModelFactory, permissionManager, resourcesManager, deviceUtils)
            else -> super.instantiate(classLoader, className)
        }
    }
}