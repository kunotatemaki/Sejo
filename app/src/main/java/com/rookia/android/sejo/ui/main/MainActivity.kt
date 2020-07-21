package com.rookia.android.sejo.ui.main

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.androidutils.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.MainGraphDirections
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ActivityMainBinding
import com.rookia.android.sejo.ui.login.LoginStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        enum class RegisterScreens(val id: Int) {
            VALIDATE_PHONE(R.id.validateNumberFragment),
            CREATE_PIN(R.id.pinCreationStep1Fragment),
            SET_PERSONAL_INFO(R.id.personalInfoFragment)
        }

        enum class BottomMenuType(val id: Int) {
            DASHBOARD(R.id.selectedGroupFragment),
            MEMBERS(R.id.membersFragment),
            PAYMENTS(R.id.paymentsFragment),
            MORE(R.id.moreFragment)
        }
    }

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var loginStatus: LoginStatus

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

    }

    override fun onStart() {
        super.onStart()

        if (needToNavigateToRegister()) {
            navigateToRegisterFlow()
        } else if (intent.extras == null && preferencesManager.containsKey(Constants.UserData.LAST_USED_GROUP_TAG).not()) {
//            navigateToGroupsFragment()
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

    fun showBadgeInBottomMenu(type: BottomMenuType, number: Int? = null) {
        val badge = binding.bottomNavigation.getOrCreateBadge(type.id)
        number?.let {
            badge.number = number
        }
    }

    fun removeBadgeFromBottomMenu(type: BottomMenuType) {
        binding.bottomNavigation.removeBadge(type.id)
    }

    fun hideNavigationBar() {
        binding.bottomNavigation.gone()
    }

    fun showNavigationBar() {
        binding.bottomNavigation.visible()
    }

    fun navigateToFragment(fragment: BottomMenuType) {
        when (fragment) {
            BottomMenuType.DASHBOARD -> binding.bottomNavigation.selectedItemId =
                R.id.selectedGroupFragment
            BottomMenuType.MEMBERS -> binding.bottomNavigation.selectedItemId =
                R.id.membersFragment
            BottomMenuType.PAYMENTS -> binding.bottomNavigation.selectedItemId =
                R.id.paymentsFragment
            BottomMenuType.MORE -> binding.bottomNavigation.selectedItemId =
                R.id.moreFragment
        }
    }

    fun enableSelectedGroupBottomIcons() {
        with(binding.bottomNavigation.menu){
            findItem(R.id.selectedGroupFragment).isEnabled = true
            findItem(R.id.membersFragment).isEnabled = true
            findItem(R.id.paymentsFragment).isEnabled = true
        }
    }

}
