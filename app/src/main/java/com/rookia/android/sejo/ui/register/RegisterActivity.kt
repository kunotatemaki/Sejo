package com.rookia.android.sejo.ui.register

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
import com.rookia.android.sejo.databinding.ActivityRegisterBinding
import com.rookia.android.sejo.ui.common.BaseActivity
import com.rookia.android.sejo.ui.fragmentfactories.RegisterFragmentFactory
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var fragmentFactory: RegisterFragmentFactory

    private lateinit var binding: ActivityRegisterBinding

    override fun getFragmentFactory(): FragmentFactory? = fragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

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
