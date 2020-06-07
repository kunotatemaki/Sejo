package com.rookia.android.sejo.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.rookia.android.sejo.MainGraphDirections
import com.rookia.android.sejo.ui.login.LoginFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import com.rookia.android.sejo.ui.main.MainActivity
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

abstract class BaseFragment(layoutId: Int, protected val loginStatus: LoginStatus) :
    Fragment(layoutId) {

    protected var forceLoginBeforeLaunchingThisFragment = false

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideLoading()
        //navigate to login if needed
        Timber.d("cretino base checks ${this.javaClass.simpleName}: ${loginStatus.needToLogin()} - ${this !is LoginFragment} - ${(activity as? MainActivity)?.needToNavigateToRegister() != true}")
        if (forceLoginBeforeLaunchingThisFragment || (loginStatus.needToLogin() && this !is LoginFragment && (activity as? MainActivity)?.needToNavigateToRegister() != true)) {
            forceLoginBeforeLaunchingThisFragment = false
            findNavController().navigate(MainGraphDirections.actionGlobalLoginFragment())
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    doOnBackPressed()
                }
            })
    }

    protected fun setToolbar(
        toolbar: MaterialToolbar,
        showBackArrow: Boolean,
        title: String? = null
    ) {
        (activity as? MainActivity)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(showBackArrow)
                it.setDisplayShowTitleEnabled(title != null)
                it.title = title
            }
        }
    }

    protected fun showLoading() {
        (activity as? MainActivity)?.showLoading()
    }

    protected fun hideLoading() {
        (activity as? MainActivity)?.hideLoading()
    }

    protected open fun doOnBackPressed() {
        findNavController().popBackStack()
    }

}