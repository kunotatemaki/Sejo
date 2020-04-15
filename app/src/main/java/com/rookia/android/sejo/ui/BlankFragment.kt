package com.rookia.android.sejo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rookia.android.sejo.R

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
//        show.setOnClickListener {
//            test.showPassword()
//        }
//        hide.setOnClickListener {
//            test.hidePassword()
//        }
//        error.setOnClickListener {
//            test.showErrorFeedback()
//        }
    }


}