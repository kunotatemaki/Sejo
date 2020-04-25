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


class PersonalInfoFragment constructor(private val viewModelFactory: ViewModelFactory) :
    BaseFragment(R.layout.fragment_personal_info) {

    private lateinit var binding: FragmentPersonalInfoBinding
    private lateinit var viewModel: PersonalInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalInfoBinding.bind(view)
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
                        navigateToDashboard()
                    }
                    Result.Status.ERROR -> hideLoading()
                    Result.Status.LOADING -> showLoading()
                }
            }
        })
    }

    override fun needToShowBackArrow(): Boolean = true

    private fun navigateToDashboard() {
        val direction = PersonalInfoFragmentDirections.actionPersonalInfoFragmentToLoginActivity()
        findNavController().navigate(direction)
        activity?.finish()
    }


}
