package com.rookia.android.sejo.ui.views.numerickeyboard

import android.content.Context
import androidx.annotation.CallSuper
import com.rookia.android.sejo.utils.VibrationUtils


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

abstract class NumericKeyboardActions(
    private val context: Context) {

    enum class KeyPressed(val value: Int) {
        KEY0(0),
        KEY1(1),
        KEY2(2),
        KEY3(3),
        KEY4(4),
        KEY5(5),
        KEY6(6),
        KEY7(7),
        KEY8(8),
        KEY9(9),
        KEY_BACK_FINGER(-1)

    }

    var vibrate = false

    @CallSuper
    @JvmOverloads
    open fun onKeyPressed(key: KeyPressed? = null) {
        if(vibrate) {
            VibrationUtils.vibrate(context, VIBRATION_LENGTH)
        }
    }

    @CallSuper
    open fun onLongDeleteKeyPressed(): Boolean {
        onKeyPressed()
        return true
    }

    companion object {
        private const val VIBRATION_LENGTH = 10
    }
}