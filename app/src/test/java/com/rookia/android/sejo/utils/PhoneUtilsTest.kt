package com.rookia.android.sejo.utils

import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants.SIGN_IN_PROCESS_VALIDATED_PHONE_NUMBER_TAG
import com.rookia.android.sejo.Constants.SIGN_IN_PROCESS_VALIDATED_PHONE_PREFIX_TAG
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
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

class PhoneUtilsTest {

    @MockK
    private lateinit var preferencesManager: PreferencesManager

    private lateinit var phoneUtils: PhoneUtils
    private val spanishPhoneNumber = "666666666"
    private val spanishPhonePrefix = "+34"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        phoneUtils = PhoneUtils(preferencesManager)
        every { preferencesManager.getStringFromPreferences(SIGN_IN_PROCESS_VALIDATED_PHONE_NUMBER_TAG) } returns spanishPhoneNumber
        every { preferencesManager.getStringFromPreferences(SIGN_IN_PROCESS_VALIDATED_PHONE_PREFIX_TAG) } returns spanishPhonePrefix
    }

    @After
    fun tearDown() {
    }

    @Test
    fun isValidPhoneNumber() {
        val validPhoneNumber = "656788903"
        val inValidPhoneNumberShort = "65678890334"
        val inValidPhoneNumberLong = "6567889"
        val inValidPhoneNumberWrongStart = "356788903"
        assertTrue(phoneUtils.isValidPhoneNumber(spanishPhonePrefix, validPhoneNumber))
        assertFalse(phoneUtils.isValidPhoneNumber("+44", validPhoneNumber))
        assertFalse(phoneUtils.isValidPhoneNumber(spanishPhonePrefix, inValidPhoneNumberShort))
        assertFalse(phoneUtils.isValidPhoneNumber(spanishPhonePrefix, inValidPhoneNumberLong))
        assertFalse(phoneUtils.isValidPhoneNumber(spanishPhonePrefix, inValidPhoneNumberWrongStart))
    }

    @Test
    fun isPhoneNumberYourActualPhone() {
        assertTrue(phoneUtils.isPhoneNumberYourActualPhone(spanishPhonePrefix, spanishPhoneNumber))
        assertFalse(phoneUtils.isPhoneNumberYourActualPhone("+44", spanishPhoneNumber))
    }

    @Test
    fun phoneStartsWithValidNumber() {
        //only checked for spanish numbers
        assertTrue(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "666666666"))
        assertTrue(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "766666666"))
        assertTrue(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "866666666d"))
        assertFalse(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "966666666d"))
        assertFalse(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "066666666d"))
        assertFalse(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "166666666d"))
        assertFalse(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "266666666d"))
        assertFalse(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "366666666d"))
        assertFalse(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "466666666d"))
        assertFalse(phoneUtils.phoneStartsWithValidNumber(spanishPhonePrefix, "566666666d"))
    }

    @Test
    fun formatWithSpaces() {
        assertEquals("6", phoneUtils.formatWithSpaces(spanishPhonePrefix, "6", false))
        assertEquals("66", phoneUtils.formatWithSpaces(spanishPhonePrefix, "66", false))
        assertEquals("666", phoneUtils.formatWithSpaces(spanishPhonePrefix, "666", false))
        assertEquals("666 6", phoneUtils.formatWithSpaces(spanishPhonePrefix, "6666", false))
        assertEquals("666 66", phoneUtils.formatWithSpaces(spanishPhonePrefix, "666 66", false))
        assertEquals("666 666", phoneUtils.formatWithSpaces(spanishPhonePrefix, "666 666", false))
        assertEquals("666 666 6", phoneUtils.formatWithSpaces(spanishPhonePrefix, "666 6666", false))
        assertEquals("666 666 66", phoneUtils.formatWithSpaces(spanishPhonePrefix, "666 666 66", false))
        assertEquals("666 666 666", phoneUtils.formatWithSpaces(spanishPhonePrefix, "666 666 666", false))
    }

    @Test
    fun formatWithSpacesAndPrefix() {
        assertEquals("+34 666 666 666", phoneUtils.formatWithSpaces(spanishPhonePrefix, "666666666", true))
    }
}