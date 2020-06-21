package com.rookia.android.sejo.ui.register.name

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentPersonalInfoBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment(R.layout.fragment_personal_info) {

    private lateinit var binding: FragmentPersonalInfoBinding
    private val viewModel: PersonalInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.apply {
            forceLoginBeforeLaunchingThisFragment = PersonalInfoFragmentArgs.fromBundle(this).forceLogin
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalInfoBinding.bind(view)
        setToolbar(binding.fragmentPersonalInfoToolbar)
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

    override fun needToHideNavigationBar(): Boolean = true

}
