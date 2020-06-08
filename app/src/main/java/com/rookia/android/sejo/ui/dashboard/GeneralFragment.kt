package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import kotlinx.android.synthetic.main.fragment_general.*
import timber.log.Timber

class GeneralFragment constructor(private val repository: GroupRepository, private val viewModelFactory: ViewModelFactory, loginStatus: LoginStatus) :
    BaseFragment(R.layout.fragment_general, loginStatus) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        test.setText("623")
        button1.setOnClickListener {
            navigateToGroupCreation()
//            test.showPassword()
        }
//        hide.setOnClickListener {
//            test.hidePassword()
//        }
//        error.setOnClickListener {
//            test.showErrorFeedback()
//        }

        repository.getGroups().observe(viewLifecycleOwner, Observer {
            Timber.d("")
        }
        )

    }


    private fun navigateToGroupCreation() {
        val direction =
            GeneralFragmentDirections.actionBlankFragmentToGroupCreationActivity()
        findNavController().navigate(direction)
    }
}