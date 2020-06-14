package com.rookia.android.sejo.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGeneralBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import kotlinx.android.synthetic.main.fragment_general.*
import timber.log.Timber

class GeneralFragment constructor(
    private val viewModelFactory: ViewModelFactory,
    loginStatus: LoginStatus
) :
    BaseFragment(R.layout.fragment_general, loginStatus) {

    private lateinit var viewModel: GeneralViewModel
    private lateinit var adapter: GeneralAdapter

    private lateinit var binding: FragmentGeneralBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGeneralBinding.bind(view)

        viewModel = injectViewModel(viewModelFactory)
        adapter = GeneralAdapter()
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
        binding.list.adapter = adapter

        viewModel.groups.observe(viewLifecycleOwner, Observer {
            Timber.d("")
            when(it.status){
                Result.Status.SUCCESS -> {
                    hideLoading()
                    adapter.submitList(it.data)
                }
                Result.Status.LOADING -> {
                    showLoading()
                    adapter.submitList(it.data)
                }
                Result.Status.ERROR -> {
                    hideLoading()
                }
            }


        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGroups()
    }

    private fun navigateToGroupCreation() {
        val direction =
            GeneralFragmentDirections.actionBlankFragmentToGroupCreationActivity()
        findNavController().navigate(direction)
    }
}