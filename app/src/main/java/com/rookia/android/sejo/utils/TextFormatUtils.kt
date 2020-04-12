package com.rookia.android.sejo.utils

import java.util.*
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
 
class TextFormatUtils @Inject constructor() {

    fun toMinuteSeconds(seconds: Int): String {
        val minutesFormat = seconds / 60
        val secondsFormat = seconds - minutesFormat * 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutesFormat, secondsFormat)
    }

    fun removeHyphenFromSmsCode(codeWithHyphenation: String) :String =
        codeWithHyphenation.replace("[^0-9]".toRegex(), "")

}