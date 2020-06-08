package com.rookia.android.sejo.ui.fragmentfactories

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.framework.utils.PermissionManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.androidutils.utils.DeviceUtils
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.framework.utils.FingerprintUtils
import com.rookia.android.sejo.ui.dashboard.GeneralFragment
import com.rookia.android.sejo.ui.dashboard.MembersFragment
import com.rookia.android.sejo.ui.dashboard.PaymentsFragment
import com.rookia.android.sejo.ui.groupcreation.GroupCreationMainInfoFragment
import com.rookia.android.sejo.ui.groupcreation.GroupCreationMembersFragment
import com.rookia.android.sejo.ui.login.BiometricPermissionFragment
import com.rookia.android.sejo.ui.login.LoginFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import com.rookia.android.sejo.ui.register.name.PersonalInfoFragment
import com.rookia.android.sejo.ui.register.number.ValidatePhoneNumberFragment
import com.rookia.android.sejo.ui.register.pincreation.PinCreationStep1Fragment
import com.rookia.android.sejo.ui.register.pincreation.PinCreationStep2Fragment
import com.rookia.android.sejo.ui.register.sms.ValidateSmsFragment
import com.rookia.android.sejo.utils.TextFormatUtils
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

class MainFragmentFactory @Inject constructor(
    private val groupRepository: GroupRepository,
    private val viewModelFactory: ViewModelFactory,
    private val resourcesManager: ResourcesManager,
    private val textFormatUtils: TextFormatUtils,
    private val preferencesManager: PreferencesManager,
    private val fingerprintUtils: FingerprintUtils,
    private val biometricDialog: BiometricPrompt.PromptInfo,
    private val permissionManager: PermissionManager,
    private val loginStatus: LoginStatus,
    private val deviceUtils: DeviceUtils
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            //region REGISTER
            ValidatePhoneNumberFragment::class.java -> ValidatePhoneNumberFragment(
                resourcesManager,
                loginStatus
            )
            ValidateSmsFragment::class.java -> ValidateSmsFragment(
                viewModelFactory,
                textFormatUtils,
                loginStatus
            )
            PinCreationStep1Fragment::class.java -> PinCreationStep1Fragment(
                resourcesManager,
                loginStatus
            )
            PinCreationStep2Fragment::class.java -> PinCreationStep2Fragment(
                resourcesManager,
                viewModelFactory,
                loginStatus
            )
            PersonalInfoFragment::class.java -> PersonalInfoFragment(
                viewModelFactory,
                loginStatus
            )
            //endregion
            //region LOGIN
            LoginFragment::class.java -> LoginFragment(
                viewModelFactory,
                fingerprintUtils,
                biometricDialog,
                preferencesManager,
                resourcesManager,
                loginStatus
            )
            BiometricPermissionFragment::class.java -> BiometricPermissionFragment(
                preferencesManager,
                loginStatus
            )
            //endregion
            //region GROUP CREATION
            GroupCreationMainInfoFragment::class.java -> GroupCreationMainInfoFragment(loginStatus)
            GroupCreationMembersFragment::class.java -> GroupCreationMembersFragment(viewModelFactory, permissionManager, resourcesManager, deviceUtils, loginStatus)
            //endregion
            GeneralFragment::class.java -> GeneralFragment(
                groupRepository,
                viewModelFactory,
                loginStatus
            )
            MembersFragment::class.java -> MembersFragment(
                viewModelFactory,
                loginStatus
            )
            PaymentsFragment::class.java -> PaymentsFragment(
                viewModelFactory,
                loginStatus
            )
            //region DASHBOARD

            else -> super.instantiate(classLoader, className)
        }
    }
}