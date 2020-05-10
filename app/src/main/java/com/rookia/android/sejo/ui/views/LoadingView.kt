package com.rookia.android.sejo.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ComponentLoadingBinding


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */

class LoadingView : FrameLayout {
    private lateinit var binding: ComponentLoadingBinding

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if(isInEditMode){
            inflater.inflate(R.layout.component_loading, this, true)
        } else {
            binding = ComponentLoadingBinding.inflate(inflater, this, true)
        }

    }

    fun setText(text: String?) {
        if(text.isNullOrBlank()) {
            binding.screenLoadingSpinnerText.gone()
        } else {
            binding.screenLoadingSpinnerText.visible()
        }
        binding.screenLoadingSpinnerText.text = text
    }

}