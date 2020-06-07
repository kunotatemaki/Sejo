package com.rookia.android.sejo.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AnimationUtils
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
import com.rookia.android.sejo.utils.VibrationUtils


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
    private var introducedPin: String = ""

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
        if (isInEditMode) {
            inflater.inflate(R.layout.component_sms_code, this, true)
        } else {
            binding = ComponentSmsCodeBinding.inflate(inflater, this, true)

        }
    }

    var text: String
        get() = introducedPin
        set(text) {
            if (text.toIntOrNull() != null) {
                introducedPin = text
                setUIForText()
            }
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

    private fun setUIForText() {

        //Set text wherever necessary
        for (i in introducedPin.indices) {
            with(binding) {
                when (i) {
                    0 -> {
                        setDigit(
                            introducedPin[i].toString(),
                            componentSmsCodeText0Label,
                            componentSmsCodeText0Bullet
                        )
                    }
                    1 -> {
                        setDigit(
                            introducedPin[i].toString(),
                            componentSmsCodeText1Label,
                            componentSmsCodeText1Bullet
                        )
                    }
                    2 -> {
                        setDigit(
                            introducedPin[i].toString(),
                            componentSmsCodeText2Label,
                            componentSmsCodeText2Bullet
                        )
                    }
                    3 -> {
                        setDigit(
                            introducedPin[i].toString(),
                            componentSmsCodeText3Label,
                            componentSmsCodeText3Bullet
                        )
                    }
                    4 -> {
                        setDigit(
                            introducedPin[i].toString(),
                            componentSmsCodeText4Label,
                            componentSmsCodeText4Bullet
                        )
                    }
                    5 -> {
                        setDigit(
                            introducedPin[i].toString(),
                            componentSmsCodeText5Label,
                            componentSmsCodeText5Bullet
                        )
                    }
                }
            }
        }

        //Set remaining bullets for the remaining text
        for (i in introducedPin.length until Constants.SMS_PIN_LENGTH) {
            with(binding) {
                when (i) {
                    0 -> setBullet(
                        componentSmsCodeText0Label,
                        componentSmsCodeText0Bullet
                    )
                    1 -> setBullet(
                        componentSmsCodeText1Label,
                        componentSmsCodeText1Bullet
                    )
                    2 -> setBullet(
                        componentSmsCodeText2Label,
                        componentSmsCodeText2Bullet
                    )
                    3 -> setBullet(
                        componentSmsCodeText3Label,
                        componentSmsCodeText3Bullet
                    )
                    4 -> setBullet(
                        componentSmsCodeText4Label,
                        componentSmsCodeText4Bullet
                    )
                    5 -> setBullet(
                        componentSmsCodeText5Label,
                        componentSmsCodeText5Bullet
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

    fun displayWrongCode() {
        val message = resources.getString(R.string.validate_sms_error_label)
        displayError(message)
    }

    fun displayExpiredCode() {
        val message = resources.getString(R.string.validate_sms_expired_label)
        displayError(message)
    }

    private fun displayError(message: String) {
        binding.componentSmsCodeError.visible()
        binding.componentSmsCodeError.text = message
        setBoxContainerBackground(R.drawable.bg_component_sms_edittext_error)
        displayColorInDigit(binding.componentSmsCodeText0Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsCodeText1Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsCodeText2Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsCodeTextDashLabel, R.color.red_error)
        displayColorInDigit(binding.componentSmsCodeText3Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsCodeText4Label, R.color.red_error)
        displayColorInDigit(binding.componentSmsCodeText5Label, R.color.red_error)
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        VibrationUtils.patternVibrate(context, Constants.ERROR_VIBRATION_PATTERN)
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
        binding.componentSmsCodeError.invisible()
        setBoxContainerBackground(R.drawable.bg_component_sms_edittext)
        displayColorInDigit(binding.componentSmsCodeText0Label, R.color.black)
        displayColorInDigit(binding.componentSmsCodeText1Label, R.color.black)
        displayColorInDigit(binding.componentSmsCodeText2Label, R.color.black)
        displayColorInDigit(binding.componentSmsCodeTextDashLabel, R.color.grey_400)
        displayColorInDigit(binding.componentSmsCodeText3Label, R.color.black)
        displayColorInDigit(binding.componentSmsCodeText4Label, R.color.black)
        displayColorInDigit(binding.componentSmsCodeText5Label, R.color.black)
    }

    private fun setBoxContainerBackground(resId: Int) {

        val bottom: Int = binding.componentSmsCodeBoxContainer.paddingBottom
        val top: Int = binding.componentSmsCodeBoxContainer.paddingTop
        val right: Int = binding.componentSmsCodeBoxContainer.paddingRight
        val left: Int = binding.componentSmsCodeBoxContainer.paddingLeft
        binding.componentSmsCodeBoxContainer.setBackgroundResource(resId)
        binding.componentSmsCodeBoxContainer.setPadding(left, top, right, bottom)
    }

    fun setPhoneText(phonePrefix: String, phoneNumber: String) {
        binding.phoneNumber = String.format(
            resources.getString(R.string.component_validate_sms_title),
            "$phonePrefix$phoneNumber"
        )
    }

    fun addDigit(digit: Char) {
        if(introducedPin.length < 6) {
            introducedPin += digit
            setUIForText()
        }
    }

    fun deleteDigit() {
        if (introducedPin.isNotEmpty()) {
            introducedPin = introducedPin.dropLast(1)
        }
        setUIForText()
    }

    fun deletePin() {
        introducedPin = ""
        setUIForText()
    }

}