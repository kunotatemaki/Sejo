package com.rookia.android.sejo.ui.common

import androidx.fragment.app.Fragment


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

open class BaseFragment(layoutId: Int): Fragment(layoutId) {

    protected fun showLoading() {
        (activity as? BaseActivity)?.showLoading()
    }

    protected fun hideLoading() {
        (activity as? BaseActivity)?.hideLoading()
    }
}