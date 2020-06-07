package com.rookia.android.sejo.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ActivityLoginBinding
import com.rookia.android.sejo.ui.common.BaseActivity
import com.rookia.android.sejo.ui.fragmentfactories.LoginFragmentFactory
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    var loginFromAnywhere = false
        private set

    @Inject
    lateinit var fragmentFactory: LoginFragmentFactory

    private lateinit var binding: ActivityLoginBinding

    override fun getFragmentFactory(): FragmentFactory? = fragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginStatus.avoidGoingToLogin()
        if (intent.hasExtra(REDIRECTED_TO_LOGIN)) {
            loginFromAnywhere = intent.getBooleanExtra(REDIRECTED_TO_LOGIN, false)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.hasExtra(REDIRECTED_TO_LOGIN) == true) {
            loginFromAnywhere = intent.getBooleanExtra(REDIRECTED_TO_LOGIN, false)
        }
    }
    override fun onBackPressed() {

    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        supportFragmentManager.findFragmentById(R.id.fragment_container)?.findNavController()
            ?.let { navController ->
                setupActionBarWithNavController(navController)
            }
    }

    override fun showLoading() {
        binding.loadingView.visible()
    }

    override fun hideLoading() {
        binding.loadingView.gone()
    }
}
