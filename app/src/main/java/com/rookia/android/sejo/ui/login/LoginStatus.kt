package com.rookia.android.sejo.ui.login


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

class LoginStatus {

    private var _launchLogin = true
    fun needToLogin(): Boolean = _launchLogin
    fun loginLaunched() {
        _launchLogin = false
    }
    fun launchLogin() {
        _launchLogin = true
    }

}