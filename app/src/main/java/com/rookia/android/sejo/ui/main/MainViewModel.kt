package com.rookia.android.sejo.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.sejo.Constants


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

class MainViewModel @ViewModelInject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {

    fun needToNavigateToRegister(): Boolean {
        val validatedPin = preferencesManager.getBooleanFromPreferences(Constants.Navigation.PIN_SENT_TAG)
        val validatedPhone =
            preferencesManager.getBooleanFromPreferences(Constants.Navigation.VALIDATED_PHONE_TAG)
        val personalInfo = preferencesManager.getBooleanFromPreferences(Constants.Navigation.PERSONAL_INFO_TAG)
        return (validatedPin.not() || validatedPhone.not() || personalInfo.not())

    }

    fun getRegisterDestinationScreen(): MainActivity.Companion.RegisterScreens {

        val validatedPin =
            preferencesManager.getBooleanFromPreferences(Constants.Navigation.PIN_SENT_TAG)
        val validatedPhone =
            preferencesManager.getBooleanFromPreferences(Constants.Navigation.VALIDATED_PHONE_TAG)
        return when {
            validatedPin && validatedPhone -> MainActivity.Companion.RegisterScreens.SET_PERSONAL_INFO
            validatedPhone -> MainActivity.Companion.RegisterScreens.CREATE_PIN
            else -> MainActivity.Companion.RegisterScreens.VALIDATE_PHONE
        }
    }
}