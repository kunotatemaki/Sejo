package com.rookia.android.sejo.ui.fragmentfactories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.ui.register.number.ValidatePhoneNumberFragment
import com.rookia.android.sejo.ui.register.passwordcreation.PasswordCreationStep1Fragment
import com.rookia.android.sejo.ui.register.passwordcreation.PasswordCreationStep2Fragment
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

class RegisterFragmentFactory @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val resourcesManager: ResourcesManager,
    private val textFormatUtils: TextFormatUtils
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            ValidatePhoneNumberFragment::class.java -> ValidatePhoneNumberFragment(
                viewModelFactory,
                resourcesManager
            )
            ValidateSmsFragment::class.java -> ValidateSmsFragment(
                viewModelFactory,
                textFormatUtils
            )
            PasswordCreationStep1Fragment::class.java -> PasswordCreationStep1Fragment(
                resourcesManager
            )
            PasswordCreationStep2Fragment::class.java -> PasswordCreationStep2Fragment(
                resourcesManager,
                viewModelFactory
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}