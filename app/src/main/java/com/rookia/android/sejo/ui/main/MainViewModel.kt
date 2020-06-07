package com.rookia.android.sejo.ui.main

import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import javax.inject.Inject


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

class MainViewModel @Inject constructor(private val preferencesManager: PreferencesManager): ViewModel() {



    fun needToNavigateToRegister(): Boolean {
        val validatedPin = preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_PIN_SENT_TAG)
        val validatedPhone =
            preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_VALIDATED_PHONE_TAG)
        val personalInfo = preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_PERSONAL_INFO_TAG)
        return (validatedPin.not() || validatedPhone.not() || personalInfo.not())
    }

    fun getRegisterDestinationScreen(): MainActivity.Companion.RegisterScreens {

        val validatedPin =
            preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_PIN_SENT_TAG)
        val validatedPhone =
            preferencesManager.getBooleanFromPreferences(Constants.NAVIGATION_VALIDATED_PHONE_TAG)
        return when {
            validatedPin && validatedPhone -> MainActivity.Companion.RegisterScreens.SET_PERSONAL_INFO
            validatedPhone -> MainActivity.Companion.RegisterScreens.CREATE_PIN
            else -> MainActivity.Companion.RegisterScreens.VALIDATE_PHONE
        }
    }
}