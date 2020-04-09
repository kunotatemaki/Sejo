package com.rookia.android.sejo.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.invisible
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ComponentSmsCodeBinding


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

class SmsCodeView : ConstraintLayout {
    private lateinit var binding: ComponentSmsCodeBinding
    private val introducedPin: SpannableStringBuilder? = null
    private var listener: OnTextChangeListener? = null

    interface OnTextChangeListener {
        fun onText(newText: String?)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ComponentSmsCodeBinding.inflate(inflater, this, true)
        binding.componentBnextSmsEdittextHiddenEdittext.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                val cleanText = editable.toString().replace("[^\\d]".toRegex(), "")
                setUIForText(cleanText)
                listener?.onText(cleanText)
            }
        })

        binding.componentBnextSmsEdittextHiddenEdittext.postDelayed({
            binding.componentBnextSmsEdittextHiddenEdittext.clearFocus()
            binding.componentBnextSmsEdittextHiddenEdittext.requestFocus()
        }, 0)
    }

    var text: String
        get() = introducedPin.toString()
        set(text) {
            binding.componentBnextSmsEdittextHiddenEdittext.setText(text)
            binding.componentBnextSmsEdittextHiddenEdittext.setSelection(text.length)
        }

    fun setOnTextChangeListener(listener: OnTextChangeListener?) {
        this.listener = listener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
        return true
    }

    fun setUIForText(text: String) {

        //Set text wherever necessary
        for (i in text.indices) {
            with(binding) {
                when (i) {
                    0 -> {
                        setDigit(
                            text[i].toString(),
                            componentBnextSmsEdittextText0Label,
                            componentBnextSmsEdittextText0Bullet
                        )
                    }
                    1 -> {
                        setDigit(
                            text[i].toString(),
                            componentBnextSmsEdittextText1Label,
                            componentBnextSmsEdittextText1Bullet
                        )
                    }
                    2 -> {
                        setDigit(
                            text[i].toString(),
                            componentBnextSmsEdittextText2Label,
                            componentBnextSmsEdittextText2Bullet
                        )
                    }
                    3 -> {
                        setDigit(
                            text[i].toString(),
                            componentBnextSmsEdittextText3Label,
                            componentBnextSmsEdittextText3Bullet
                        )
                    }
                    4 -> {
                        setDigit(
                            text[i].toString(),
                            componentBnextSmsEdittextText4Label,
                            componentBnextSmsEdittextText4Bullet
                        )
                    }
                    5 -> {
                        setDigit(
                            text[i].toString(),
                            componentBnextSmsEdittextText5Label,
                            componentBnextSmsEdittextText5Bullet
                        )
                    }
                }
            }
        }

        //Set remaining bullets for the remaining text
        for (i in text.length until Constants.SMS_PIN_LENGTH) {
            with(binding) {
                when (i) {
                    0 -> setBullet(
                        componentBnextSmsEdittextText0Label,
                        componentBnextSmsEdittextText0Bullet
                    )
                    1 -> setBullet(
                        componentBnextSmsEdittextText1Label,
                        componentBnextSmsEdittextText1Bullet
                    )
                    2 -> setBullet(
                        componentBnextSmsEdittextText2Label,
                        componentBnextSmsEdittextText2Bullet
                    )
                    3 -> setBullet(
                        componentBnextSmsEdittextText3Label,
                        componentBnextSmsEdittextText3Bullet
                    )
                    4 -> setBullet(
                        componentBnextSmsEdittextText4Label,
                        componentBnextSmsEdittextText4Bullet
                    )
                    5 -> setBullet(
                        componentBnextSmsEdittextText5Label,
                        componentBnextSmsEdittextText5Bullet
                    )
                }
            }
        }
    }

    private fun setDigit(text: String, digitPlaceHolder: TextView, bullet: ImageView) {
        bullet.invisible()
        digitPlaceHolder.visible()
        digitPlaceHolder.text = text
    }

    private fun setBullet(digitPlaceHolder: TextView, bullet: ImageView) {
        digitPlaceHolder.invisible()
        bullet.visible()
    }

    fun displayError() {
        binding.componentBnextSmsEdittextError.visible()
        setBoxContainerBackground(R.drawable.bg_component_sejo_sms_edittext_error)
        displayColorInDigit(binding.componentBnextSmsEdittextText0Label, R.color.red)
        displayColorInDigit(binding.componentBnextSmsEdittextText1Label, R.color.red)
        displayColorInDigit(binding.componentBnextSmsEdittextText2Label, R.color.red)
        displayColorInDigit(binding.componentBnextSmsEdittextTextDashLabel, R.color.red)
        displayColorInDigit(binding.componentBnextSmsEdittextText3Label, R.color.red)
        displayColorInDigit(binding.componentBnextSmsEdittextText4Label, R.color.red)
        displayColorInDigit(binding.componentBnextSmsEdittextText5Label, R.color.red)
    }

    private fun displayColorInDigit(view: TextView, colorResValue: Int) {
        view.setTextColor(
            ContextCompat.getColor(
                this.context,
                colorResValue
            )
        )
    }

    fun hideError() {
        binding.componentBnextSmsEdittextError.gone()
        setBoxContainerBackground(R.drawable.bg_component_sejo_sms_edittext)
        displayColorInDigit(binding.componentBnextSmsEdittextText0Label, R.color.black)
        displayColorInDigit(binding.componentBnextSmsEdittextText1Label, R.color.black)
        displayColorInDigit(binding.componentBnextSmsEdittextText2Label, R.color.black)
        displayColorInDigit(binding.componentBnextSmsEdittextTextDashLabel, R.color.grey_4)
        displayColorInDigit(binding.componentBnextSmsEdittextText3Label, R.color.black)
        displayColorInDigit(binding.componentBnextSmsEdittextText4Label, R.color.black)
        displayColorInDigit(binding.componentBnextSmsEdittextText5Label, R.color.black)
    }

    private fun setBoxContainerBackground(resId: Int) {

        //This is a LinearLayout bug:
        //https://stackoverflow.com/a/5890473/4021998
        val bottom: Int = binding.componentBnextSmsEdittextBoxContainer.paddingBottom
        val top: Int = binding.componentBnextSmsEdittextBoxContainer.paddingTop
        val right: Int = binding.componentBnextSmsEdittextBoxContainer.paddingRight
        val left: Int = binding.componentBnextSmsEdittextBoxContainer.paddingLeft
        binding.componentBnextSmsEdittextBoxContainer.setBackgroundResource(resId)
        binding.componentBnextSmsEdittextBoxContainer.setPadding(left, top, right, bottom)
    }

}