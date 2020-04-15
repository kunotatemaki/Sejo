package com.rookia.android.sejo.ui.common

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.MainActivityBinding
import com.rookia.android.sejo.ui.fragmentfactories.RegisterFragmentFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var fragmentFactory: RegisterFragmentFactory

    private lateinit var binding: MainActivityBinding

    override fun getFragmentFactory(): FragmentFactory? = fragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        supportFragmentManager.findFragmentById(R.id.fragment_container)?.findNavController()
            ?.let { navController ->
                setupActionBarWithNavController(navController)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        binding.loadingView.visible()
    }

    override fun hideLoading() {
        binding.loadingView.gone()
    }

}
