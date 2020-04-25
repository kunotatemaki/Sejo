package com.rookia.android.sejo.ui.fragmentfactories

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.framework.utils.FingerprintUtils
import com.rookia.android.sejo.ui.login.BiometricPermissionFragment
import com.rookia.android.sejo.ui.login.LoginFragment
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

class LoginFragmentFactory @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val fingerprintUtils: FingerprintUtils,
    private val biometricDialog: BiometricPrompt.PromptInfo,
    private val preferencesManager: PreferencesManager,
    private val resourcesManager: ResourcesManager
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            LoginFragment::class.java -> LoginFragment(
                viewModelFactory,
                fingerprintUtils,
                biometricDialog,
                preferencesManager,
                resourcesManager
            )
            BiometricPermissionFragment::class.java -> BiometricPermissionFragment(preferencesManager)
            else -> super.instantiate(classLoader, className)
        }
    }
}