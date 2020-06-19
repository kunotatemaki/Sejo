package com.rookia.android.sejo.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.sejo.MainGraphDirections
import com.rookia.android.sejo.ui.login.LoginFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import com.rookia.android.sejo.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
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

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId), CoroutineScope by MainScope() {


    @Inject
    protected lateinit var loginStatus: LoginStatus

    @Inject
    protected lateinit var preferencesManager: PreferencesManager

    @Inject
    protected lateinit var resourcesManager: ResourcesManager

    protected var forceLoginBeforeLaunchingThisFragment = false

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLogin()
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideLoading()
        //navigate to login if needed
        checkLogin()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    doOnBackPressed()
                }
            })
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        checkLogin()
        Timber.d("cretino ${this.javaClass.simpleName} - ${activity != null}")
        (activity as? MainActivity)?.apply {
            if (needTohideNavigationBar()) {
                Timber.d("cretino ${this.javaClass.simpleName} - hide")
                hideNavigationBar()
            } else {
                Timber.d("cretino ${this.javaClass.simpleName} - show")
                showNavigationBar()
            }
        }
    }

    abstract fun needTohideNavigationBar(): Boolean

    private fun checkLogin() {
        if (forceLoginBeforeLaunchingThisFragment || (loginStatus.needToLogin() && this !is LoginFragment && (activity as? MainActivity)?.needToNavigateToRegister() != true)) {
            forceLoginBeforeLaunchingThisFragment = false
            loginStatus.avoidGoingToLogin()
            findNavController().navigate(MainGraphDirections.actionGlobalLoginFragment())
        }
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