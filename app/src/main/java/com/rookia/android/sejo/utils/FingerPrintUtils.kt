package com.rookia.android.sejo.utils

import android.content.Context
import androidx.biometric.BiometricManager
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

class FingerprintUtils @Inject constructor(private val context: Context){

    fun isFingerprintSupported(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                true
            else ->
                false
        }
    }

}