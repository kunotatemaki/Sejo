package com.rookia.android.sejo.framework.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.rookia.android.sejo.utils.TextFormatUtils
import java.util.regex.Matcher
import java.util.regex.Pattern


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


class SMSBroadcastReceiver constructor(
    private val code: MutableLiveData<String>,
    private val textFormatUtils: TextFormatUtils
) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            intent.extras?.let { extras ->
                val status: Status? = extras.get(SmsRetriever.EXTRA_STATUS) as Status?
                when (status?.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        extras.getString(SmsRetriever.EXTRA_SMS_MESSAGE)?.let { message ->
                            extractCodeFromMessage(message)
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun getCode(): LiveData<String> = code

    fun extractCodeFromMessage(message: String) {
        val p: Pattern = Pattern.compile("\\d{3}-\\d{3}")
        val m: Matcher = p.matcher(message)
        if (m.find()) {
            val codeWithHyphenation = m.group()
            val finalCode = textFormatUtils.removeHyphenFromSmsCode(codeWithHyphenation)
            code.postValue(finalCode)
        }
    }
}