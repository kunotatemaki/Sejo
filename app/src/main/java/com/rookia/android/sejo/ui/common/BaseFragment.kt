package com.rookia.android.sejo.ui.common

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar


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

    protected fun setToolbar(toolbar: MaterialToolbar, showBackArrow: Boolean, title: String? = null){
        (activity as? BaseActivity)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(showBackArrow)
                it.setDisplayShowTitleEnabled(title != null)
                it.title = title
            }
        }
    }

    protected fun showLoading() {
        (activity as? BaseActivity)?.showLoading()
    }

    protected fun hideLoading() {
        (activity as? BaseActivity)?.hideLoading()
    }

}