package com.rookia.android.sejo.ui.views.numerickeyboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ComponentNumericKeyboardViewBinding
import com.rookia.android.sejo.ui.views.numerickeyboard.NumericKeyboardActions.KeyPressed.*


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

class NumericKeyboardView : ConstraintLayout {
    lateinit var binding: ComponentNumericKeyboardViewBinding
    private var vibrate = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ComponentNumericKeyboardViewBinding.inflate(inflater, this, true)
        binding.componentNumericKeyboardViewNumber0.setOnClickListener { binding.action?.onKeyPressed(KEY0) }
        binding.componentNumericKeyboardViewNumber1.setOnClickListener { binding.action?.onKeyPressed(KEY1) }
        binding.componentNumericKeyboardViewNumber2.setOnClickListener { binding.action?.onKeyPressed(KEY2) }
        binding.componentNumericKeyboardViewNumber3.setOnClickListener { binding.action?.onKeyPressed(KEY3) }
        binding.componentNumericKeyboardViewNumber4.setOnClickListener { binding.action?.onKeyPressed(KEY4) }
        binding.componentNumericKeyboardViewNumber5.setOnClickListener { binding.action?.onKeyPressed(KEY5) }
        binding.componentNumericKeyboardViewNumber6.setOnClickListener { binding.action?.onKeyPressed(KEY6) }
        binding.componentNumericKeyboardViewNumber7.setOnClickListener { binding.action?.onKeyPressed(KEY7) }
        binding.componentNumericKeyboardViewNumber8.setOnClickListener { binding.action?.onKeyPressed(KEY8) }
        binding.componentNumericKeyboardViewNumber9.setOnClickListener { binding.action?.onKeyPressed(KEY9) }
        binding.componentNumericKeyboardViewDelete.setOnClickListener { binding.action?.onKeyPressed(KEY_BACK_FINGER)}
        binding.componentNumericKeyboardViewDelete.setOnLongClickListener {
            binding.action?.onLongDeleteKeyPressed()
            true
        }
    }


    fun setAction(action: NumericKeyboardActions) {
        binding.action = action
    }


    fun setBackspace() {
        binding.run { componentNumericKeyboardViewDeleteImage.setImageResource(R.drawable.ic_component_numeric_keyboard_delete) }
    }

    fun setFingerprint() {
        binding.componentNumericKeyboardViewDeleteImage.setImageResource(R.drawable.ic_component_numeric_keyboard_fingerprint)
    }

    fun showRecoverPassword() {
        binding.componentNumericKeyboardRecoverPassword.visible()
    }

    fun hideRecoverPassword() {
        binding.componentNumericKeyboardRecoverPassword.gone()
    }

    /**
     * Returns the view associated with each key
     * @param key
     * @return
     */
    fun getKeyView(key: NumericKeyboardActions.KeyPressed): View {
        return when (key) {
            KEY0 -> binding.componentNumericKeyboardViewNumber0
            KEY1 -> binding.componentNumericKeyboardViewNumber1
            KEY2 -> binding.componentNumericKeyboardViewNumber2
            KEY3 -> binding.componentNumericKeyboardViewNumber3
            KEY4 -> binding.componentNumericKeyboardViewNumber4
            KEY5 -> binding.componentNumericKeyboardViewNumber5
            KEY6 -> binding.componentNumericKeyboardViewNumber6
            KEY7 -> binding.componentNumericKeyboardViewNumber7
            KEY8 -> binding.componentNumericKeyboardViewNumber8
            KEY9 -> binding.componentNumericKeyboardViewNumber9
            KEY_BACK_FINGER -> binding.componentNumericKeyboardViewDelete
        }
    }
}