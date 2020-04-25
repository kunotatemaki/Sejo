package com.rookia.android.sejo.ui.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.rookia.android.sejo.ui.login.LoginActivity
import com.rookia.android.sejo.ui.login.LoginStatus
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected lateinit var loginStatus: LoginStatus

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    abstract fun getFragmentFactory(): FragmentFactory?

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        getFragmentFactory()?.let {
            supportFragmentManager.fragmentFactory = it
        }
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if(loginStatus.needToLogin()){
            loginStatus.loginLaunched()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
        }
    }

    fun showKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    abstract fun showLoading()

    abstract fun hideLoading()

}
