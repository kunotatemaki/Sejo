package com.rookia.android.sejo.framework.utils

import android.content.Context
import androidx.biometric.BiometricManager
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
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

class FingerprintUtils @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferencesManager: PreferencesManager
) {

    fun isFingerprintSupported(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    fun shouldShowFingerPrintScreen(): Boolean {
        val useFingerPrint =
            preferencesManager.getBooleanFromPreferences(Constants.UserData.BIOMETRICS_TAG)
        val isFingerprintSupported = isFingerprintSupported()
        val pwdBytes = preferencesManager.getEncryptedStringFromPreferences(Constants.UserData.PIN_TAG, Constants.UserData.PIN_ALIAS)
        return useFingerPrint && isFingerprintSupported && pwdBytes.isNullOrBlank().not()
    }
}