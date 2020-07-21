package com.rookia.android.sejo.framework.receivers

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.androidutils.utils.Encryption
import com.rookia.android.sejo.Constants


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, May 2020
 *
 *
 */

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        val preferencesManager =
            PreferencesManager(this.applicationContext, Encryption(this.applicationContext))
        preferencesManager.setStringIntoPreferences(Constants.UserData.PUSH_TOKEN_TAG, token)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }
}