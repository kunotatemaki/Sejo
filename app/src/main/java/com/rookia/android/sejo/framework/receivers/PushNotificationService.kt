package com.rookia.android.sejo.framework.receivers

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rookia.android.androidutils.framework.preferences.PreferencesManagerImpl
import com.rookia.android.androidutils.framework.utils.security.EncryptionImpl
import com.rookia.android.sejo.Constants
import timber.log.Timber


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
            PreferencesManagerImpl(this.applicationContext, EncryptionImpl(this.applicationContext))
        Timber.d("cretino $token")
        preferencesManager.setStringIntoPreferences(Constants.PUSH_TOKEN_TAG, token)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Timber.d("cretinooooo")
        super.onMessageReceived(p0)
    }
}