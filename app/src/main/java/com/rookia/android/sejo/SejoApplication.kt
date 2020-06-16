package com.rookia.android.sejo

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.FirebaseApp
import com.rookia.android.sejo.ui.login.LoginStatus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
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

@HiltAndroidApp
class SejoApplication  : Application(), LifecycleObserver {

    @Inject
    lateinit var loginStatus: LoginStatus

    override fun onCreate() {
        super.onCreate()
        // Initialize Logging with Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        FirebaseApp.initializeApp(this)
    }

    /** A tree which logs important information for crash reporting. (Tiber) */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        loginStatus.forceNavigationThroughLogin()
    }
}
