package com.rookia.android.sejo.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.androidutils.resources.ResourcesManager
import com.rookia.android.sejo.MainGraphDirections
import com.rookia.android.sejo.ui.login.LoginFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import com.rookia.android.sejo.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
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

    @Inject
    protected lateinit var appBarConfiguration: AppBarConfiguration

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
        (activity as? MainActivity)?.apply {
            if (needToHideNavigationBar()) {
                hideNavigationBar()
            } else {
                showNavigationBar()
            }
        }
    }

    abstract fun needToHideNavigationBar(): Boolean

    private fun checkLogin() {
        if (forceLoginBeforeLaunchingThisFragment || (loginStatus.needToLogin() && this !is LoginFragment && (activity as? MainActivity)?.needToNavigateToRegister() != true)) {
            forceLoginBeforeLaunchingThisFragment = false
            loginStatus.avoidGoingToLogin()
            findNavController().navigate(MainGraphDirections.actionGlobalLoginFragment())
        }
    }

    protected fun setToolbar(
        toolbar: MaterialToolbar
    ) {
        val navController = findNavController()
        toolbar.setupWithNavController(navController, appBarConfiguration)
        (activity as? MainActivity)?.setSupportActionBar(toolbar)
    }

    protected fun forceHideBackArrow() {
        (activity as? MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    protected fun setTitle(title: String) {
        (activity as? MainActivity)?.supportActionBar?.title = title
    }

    protected fun showLoading() {
        (activity as? MainActivity)?.showLoading()
    }

    protected fun hideLoading() {
        (activity as? MainActivity)?.hideLoading()
    }

    /**
     * Method called when tha back button is pressed. Override it in the fragment to modify the behaviour
     */
    protected open fun doOnBackPressed() {
        findNavController().popBackStack()
    }

}