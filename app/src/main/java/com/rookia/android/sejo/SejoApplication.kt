package com.rookia.android.sejo

import android.util.Log
import com.rookia.android.sejo.di.components.AppComponent
import com.rookia.android.sejo.di.components.ComponentFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


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

class SejoApplication  : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<SejoApplication> {
        val mComponent: AppComponent = ComponentFactory.component(this)
        mComponent.inject(this)
        return mComponent
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Logging with Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting. (Tiber) */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
        }
    }
}
