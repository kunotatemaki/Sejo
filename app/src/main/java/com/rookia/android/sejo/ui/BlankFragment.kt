package com.rookia.android.sejo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rookia.android.sejo.R
import kotlinx.android.synthetic.main.fragment_blank.*

class BlankFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
    }


    private fun navigateToGroupCreation(){
        val direction = BlankFragmentDirections.actionBlankFragmentToGroupCreationActivity()
        findNavController().navigate(direction)
    }
}