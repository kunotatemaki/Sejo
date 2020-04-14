package com.rookia.android.sejo.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import timber.log.Timber


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

object VibrationUtils {

    /**
     * Vibrate phone for x ms
     * @param ms
     */
    @Suppress("DEPRECATION")
    fun vibrate(context: Context, ms: Int) = try {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(
                VibrationEffect.createOneShot(ms.toLong(), VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else { //deprecated in API 26
            vibrator?.vibrate(ms.toLong())
        }
    } catch (e: java.lang.NullPointerException) {
        e.printStackTrace()
        Timber.d("Couldn't vibrate.")
    }

    /**
     * Vibrate with pattern {0, 100, 0, 100} wait, vib, wait, vib
     * @param pattern
     */
    @Suppress("DEPRECATION")
    fun patternVibrate(context: Context, pattern: LongArray) =
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                vibrator?.vibrate(pattern, -1)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Timber.v("Couldn't vibrate.")
        }

}