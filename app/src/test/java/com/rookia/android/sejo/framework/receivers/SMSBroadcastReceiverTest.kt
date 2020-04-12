package com.rookia.android.sejo.framework.receivers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

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

class SMSBroadcastReceiverTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val code: MutableLiveData<String> = MutableLiveData()
    private val smsBroadcastReceiver = SMSBroadcastReceiver(code)

    @Test
    fun extractCodeFromMessage() {
        val message = "prueba de mensaje con código 123-456 en el interior"
        smsBroadcastReceiver.extractCodeFromMessage(message)
        assertEquals("123456", code.value)
    }

    @Test
    fun extractWrongFormattedCodeFromMessage() {
        val message = "prueba de mensaje con código 12-456 en el interior"
        smsBroadcastReceiver.extractCodeFromMessage(message)
        assertEquals(null, code.value)
    }
}