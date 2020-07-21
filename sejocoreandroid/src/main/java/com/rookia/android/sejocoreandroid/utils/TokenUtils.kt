package com.rookia.android.sejocoreandroid.utils

import com.rookia.android.androidutils.data.preferences.PreferencesManager
import javax.inject.Inject


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, July 2020
 *
 *
 */

class TokenUtils @Inject constructor(private val preferencesManager: PreferencesManager) {

    companion object {
        private const val TOKEN_TAG = "USER_TOKEN_TAG"
    }
    fun storeToken(token: String) {
        preferencesManager.setStringIntoPreferences(TOKEN_TAG, token)
    }

    fun getToken(): String? =
        preferencesManager.getStringFromPreferences(TOKEN_TAG)


}