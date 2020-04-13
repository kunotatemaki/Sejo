package com.rookia.android.sejo.utils

import org.junit.Test

import org.junit.Assert.*

/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 *
 * Written by Roll <raulfeliz></raulfeliz>@gmail.com>, April 2020
 */

class TextFormatUtilsTest {

    private val textFormatUtils = TextFormatUtils()

    @Test
    fun toMinuteSeconds() {
        assertEquals("23:45", textFormatUtils.toMinuteSeconds(1425))
        assertEquals("00:05", textFormatUtils.toMinuteSeconds(5))
    }

    @Test
    fun removeHyphenFromSmsCode() {
        assertEquals("123456", textFormatUtils.removeHyphenFromSmsCode("123-456"))
    }
}