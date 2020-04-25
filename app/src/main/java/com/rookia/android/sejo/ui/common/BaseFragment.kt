package com.rookia.android.sejo.ui.common

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
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

abstract class BaseFragment(layoutId: Int): Fragment(layoutId) {

    abstract fun needToShowBackArrow() : Boolean

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideLoading()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(needToShowBackArrow())
    }

    protected fun showLoading() {
        (activity as? BaseActivity)?.showLoading()
    }

    protected fun hideLoading() {
        (activity as? BaseActivity)?.hideLoading()
    }

}