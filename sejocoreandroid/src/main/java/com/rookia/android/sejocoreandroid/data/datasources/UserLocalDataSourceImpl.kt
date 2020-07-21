package com.rookia.android.sejocoreandroid.data.datasources

import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.sejocore.data.local.UserLocalDataSource
import com.rookia.android.sejocoreandroid.data.repository.RepositoryErrorHandling
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

class UserLocalDataSourceImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : UserLocalDataSource, RepositoryErrorHandling {

    companion object {
        private const val TOKEN_TAG = "USER_TOKEN_TAG"
    }

    override fun storeToken(token: String) {
        preferencesManager.setStringIntoPreferences(TOKEN_TAG, token)
    }

    override fun getToken(): String? = preferencesManager.getStringFromPreferences(TOKEN_TAG)

}