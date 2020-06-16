package com.rookia.android.sejo.ui.login

import javax.inject.Inject
import javax.inject.Singleton


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

@Singleton
class LoginStatus @Inject constructor() {

    private var _launchLogin = true
    fun needToLogin(): Boolean = _launchLogin
    fun avoidGoingToLogin() {
        _launchLogin = false
    }
    fun forceNavigationThroughLogin() {
        _launchLogin = true
    }

}