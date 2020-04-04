package com.rookia.android.sejo.ui.register.sms

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rookia.android.sejo.R

class ValidateSmsFragment : Fragment() {

    companion object {
        fun newInstance() = ValidateSmsFragment()
    }

    private lateinit var viewModel: ValidateSmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.validate_sms_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ValidateSmsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}