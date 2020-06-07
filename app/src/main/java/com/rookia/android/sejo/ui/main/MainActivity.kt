package com.rookia.android.sejo.ui.main

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.MainGraphDirections
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ActivityMainBinding
import com.rookia.android.sejo.ui.fragmentfactories.MainFragmentFactory
import com.rookia.android.sejo.ui.login.LoginStatus
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    companion object{
        enum class RegisterScreens(val id: Int){
            VALIDATE_PHONE(R.id.validateNumberFragment), CREATE_PIN(R.id.pinCreationStep1Fragment), SET_PERSONAL_INFO(R.id.personalInfoFragment)
        }
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var loginStatus: LoginStatus

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private lateinit var viewModel: MainViewModel

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = injectViewModel(viewModelFactory)


    }

    override fun onStart() {
        super.onStart()

        if (needToNavigateToRegister()) {
            navigateToRegisterFlow()
        }
        supportFragmentManager.findFragmentById(R.id.fragment_container)?.findNavController()
            ?.let { navController ->
//                setupActionBarWithNavController(navController)

            }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        supportFragmentManager.findFragmentById(R.id.fragment_container)?.findNavController()
            ?.let { navController ->
                setupActionBarWithNavController(navController)

            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }


    fun showLoading() {
        binding.loadingView.visible()
    }

    fun hideLoading() {
        binding.loadingView.gone()
    }

    fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
        }
    }

    fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    fun needToNavigateToRegister(): Boolean = viewModel.needToNavigateToRegister()

    private fun navigateToRegisterFlow() {
        with(findNavController(R.id.fragment_container)) {
            val startDestination = viewModel.getRegisterDestinationScreen()
            (graph.findNode(R.id.register_graph) as? NavGraph)?.startDestination =
                startDestination.id
            if (startDestination == RegisterScreens.SET_PERSONAL_INFO) {
                loginStatus.forceNavigationThroughLogin()
            } else {
                loginStatus.avoidGoingToLogin()
            }
            navigate(MainGraphDirections.actionGlobalValidateNumberFragment())
        }
    }

}