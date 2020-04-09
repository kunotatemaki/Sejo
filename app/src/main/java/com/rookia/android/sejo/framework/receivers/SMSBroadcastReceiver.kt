package com.rookia.android.sejo.framework.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


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

class SMSBroadcastReceiver constructor(private val code: MutableLiveData<String>): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            intent.extras?.let {
                val status: Status? = it.get(SmsRetriever.EXTRA_STATUS) as Status?
                when (status?.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        var message: String? = it.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                        //todo sacar código y pasarlo al livedata
                        code.postValue(message)
                    }
                    CommonStatusCodes.TIMEOUT -> {
                    }
                }
            }
        }
    }
}