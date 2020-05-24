package com.rookia.android.sejo.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.R
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import timber.log.Timber

class BlankFragment constructor(private val repository: GroupRepository) :
    BaseFragment(R.layout.fragment_blank), CoroutineScope by MainScope() {


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
        val direction = BlankFragmentDirections.actionBlankFragmentToGroupCreationActivity()
        findNavController().navigate(direction)
    }
}