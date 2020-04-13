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
        fun onText(newText: String)
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
        binding.componentSmsEdittextHiddenEdittext.addTextChangedListener(object :
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

        binding.componentSmsEdittextHiddenEdittext.postDelayed({
            binding.componentSmsEdittextHiddenEdittext.clearFocus()
            binding.componentSmsEdittextHiddenEdittext.requestFocus()
        }, 0)
    }

    var text: String
        get() = introducedPin.toString()
        set(text) {
            binding.componentSmsEdittextHiddenEdittext.setText(text)
            binding.componentSmsEdittextHiddenEdittext.setSelection(text.length)
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
                            componentSmsEdittextText0Label,
                            componentSmsEdittextText0Bullet
                        )
                    }
                    1 -> {
                        setDigit(
                            text[i].toString(),
                            componentSmsEdittextText1Label,
                            componentSmsEdittextText1Bullet
                        )
                    }
                    2 -> {
                        setDigit(
                            text[i].toString(),
                            componentSmsEdittextText2Label,
                            componentSmsEdittextText2Bullet
                        )
                    }
                    3 -> {
                        setDigit(
                            text[i].toString(),
                            componentSmsEdittextText3Label,
                            componentSmsEdittextText3Bullet
                        )
                    }
                    4 -> {
                        setDigit(
                            text[i].toString(),
                            componentSmsEdittextText4Label,
                            componentSmsEdittextText4Bullet
                        )
                    }
                    5 -> {
                        setDigit(
                            text[i].toString(),
                            componentSmsEdittextText5Label,
                            componentSmsEdittextText5Bullet
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
                        componentSmsEdittextText0Label,
                        componentSmsEdittextText0Bullet
                    )
                    1 -> setBullet(
                        componentSmsEdittextText1Label,
                        componentSmsEdittextText1Bullet
                    )
                    2 -> setBullet(
                        componentSmsEdittextText2Label,
                        componentSmsEdittextText2Bullet
                    )
                    3 -> setBullet(
                        componentSmsEdittextText3Label,
                        componentSmsEdittextText3Bullet
                    )
                    4 -> setBullet(
                        componentSmsEdittextText4Label,
                        componentSmsEdittextText4Bullet
                    )
                    5 -> setBullet(
                        componentSmsEdittextText5Label,
                        componentSmsEdittextText5Bullet
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

    fun displayWrongCode(){
        val message = resources.getString(R.string.validate_sms_error_label)
        displayError(message)
    }

    fun displayExpiredCode(){
        val message = resources.getString(R.string.validate_sms_expired_label)
        displayError(message)
    }

    private fun displayError(message:String) {
        binding.componentSmsEdittextError.visible()
        binding.componentSmsEdittextError.text = message
        setBoxContainerBackground(R.drawable.bg_component_sejo_sms_edittext_error)
        displayColorInDigit(binding.componentSmsEdittextText0Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsEdittextText1Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsEdittextText2Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsEdittextTextDashLabel, R.color.red_error)
        displayColorInDigit(binding.componentSmsEdittextText3Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsEdittextText4Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsEdittextText5Label, R.color.red_error)
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
        binding.componentSmsEdittextError.invisible()
        setBoxContainerBackground(R.drawable.bg_component_sejo_sms_edittext)
        displayColorInDigit(binding.componentSmsEdittextText0Label, R.color.black)
        displayColorInDigit(binding.componentSmsEdittextText1Label, R.color.black)
        displayColorInDigit(binding.componentSmsEdittextText2Label, R.color.black)
        displayColorInDigit(binding.componentSmsEdittextTextDashLabel, R.color.grey_400)
        displayColorInDigit(binding.componentSmsEdittextText3Label, R.color.black)
        displayColorInDigit(binding.componentSmsEdittextText4Label, R.color.black)
        displayColorInDigit(binding.componentSmsEdittextText5Label, R.color.black)
    }

    private fun setBoxContainerBackground(resId: Int) {

        //This is a LinearLayout bug:
        //https://stackoverflow.com/a/5890473/4021998
        val bottom: Int = binding.componentSmsEdittextBoxContainer.paddingBottom
        val top: Int = binding.componentSmsEdittextBoxContainer.paddingTop
        val right: Int = binding.componentSmsEdittextBoxContainer.paddingRight
        val left: Int = binding.componentSmsEdittextBoxContainer.paddingLeft
        binding.componentSmsEdittextBoxContainer.setBackgroundResource(resId)
        binding.componentSmsEdittextBoxContainer.setPadding(left, top, right, bottom)
    }

}