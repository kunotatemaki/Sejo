package com.rookia.android.sejo.ui.register.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rookia.android.androidutils.di.injectViewModel
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.R
import javax.inject.Inject

class ValidateSmsFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {

    private lateinit var viewModel: ValidateSmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.validate_sms_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)

        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.store()
        viewModel.getStore()
    }

}