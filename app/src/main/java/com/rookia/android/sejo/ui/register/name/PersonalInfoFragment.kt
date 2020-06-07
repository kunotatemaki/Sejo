package com.rookia.android.sejo.ui.register.name

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPersonalInfoBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.login.LoginStatus
import com.rookia.android.sejo.ui.main.MainActivity


class PersonalInfoFragment constructor(private val viewModelFactory: ViewModelFactory, loginStatus: LoginStatus) :
    BaseFragment(R.layout.fragment_personal_info, loginStatus) {

    private lateinit var binding: FragmentPersonalInfoBinding
    private lateinit var viewModel: PersonalInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        forceLoginBeforeLaunchingThisFragment = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalInfoBinding.bind(view)
        setToolbar(binding.fragmentPersonalInfoToolbar, true)
        viewModel = injectViewModel(viewModelFactory)
        binding.fragmentPersonalInfoName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.fragmentPersonalInfoButton.isEnabled = p0.isNullOrBlank().not()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        binding.fragmentPersonalInfoButton.setOnClickListener {
            viewModel.updatePersonalInfo(binding.fragmentPersonalInfoName.text.toString())
        }
        viewModel.userSentToServer.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it.status) {
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        finishRegister()
                    }
                    Result.Status.ERROR -> hideLoading()
                    Result.Status.LOADING -> showLoading()
                }
            }
        })
    }

    private fun finishRegister() {
        val registrationEntryPoints = MainActivity.Companion.RegisterScreens.values()
        registrationEntryPoints.forEach {
            if(findNavController().popBackStack(it.id, true)){
                return
            }
        }
        findNavController().popBackStack()
    }


}
