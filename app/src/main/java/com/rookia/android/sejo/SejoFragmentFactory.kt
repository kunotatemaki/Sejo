package com.rookia.android.sejo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.ui.register.number.ValidatePhoneNumberFragment
import com.rookia.android.sejo.ui.register.sms.ValidateSmsFragment
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

class SejoFragmentFactory @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val viewModelFactory: ViewModelFactory
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader,className)) {
            ValidatePhoneNumberFragment::class.java -> ValidatePhoneNumberFragment(preferencesManager)
            ValidateSmsFragment::class.java -> ValidateSmsFragment(viewModelFactory)
            else -> super.instantiate(classLoader, className)
        }
    }
}