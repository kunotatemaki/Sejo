package com.rookia.android.sejo.ui.views


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

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.rookia.android.androidutils.extensions.gone
import com.rookia.android.androidutils.extensions.visible
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ComponentPinWithKeyboardBinding
import com.rookia.android.sejo.ui.views.numerickeyboard.NumericKeyboardActions
import com.rookia.android.sejo.ui.views.numerickeyboard.NumericKeyboardActions.KeyPressed.KEY_BACK_FINGER


class PinScreen : ConstraintLayout {
    lateinit var binding: ComponentPinWithKeyboardBinding
    private var vibrate = false
    private var showRecoverPin = false
    private var showPinButton = false
    private var shouldShowFingerprintOption = false

    interface PinValidator {
        fun onPinChanged(pin: String, isCompleted: Boolean)
    }

    interface BiometricHelper {
        fun showBiometricsLogin()
        fun shouldShowFingerPrintScreen(): Boolean
    }

    private var pinValidator: PinValidator? = null
    private var biometricHelper: BiometricHelper? = null

    private val actions: NumericKeyboardActions = object : NumericKeyboardActions(context) {

        override fun onKeyPressed(key: KeyPressed?) {
            super.onKeyPressed(key)
            key ?: return
            if (key == KEY_BACK_FINGER) {
                if (shouldShowFingerprintOption && binding.componentPinScreenBullets.getPin().isBlank()) {
                    biometricHelper?.showBiometricsLogin()
                } else {
                    binding.componentPinScreenBullets.deleteChar()
                }
            } else {
                binding.componentPinScreenBullets.addChar(key.value.toString())
            }
            checkFingerprintIcon()

            //Login when the length is correct
            val currentEnteredPin = getPin()
            val pinCompleted = isPinSet()
            pinValidator?.onPinChanged(currentEnteredPin, pinCompleted)
            if (pinCompleted.not()) {
                hideError()
            }

        }

        override fun onLongDeleteKeyPressed(): Boolean {
            this.onKeyPressed(KEY_BACK_FINGER)
            binding.componentPinScreenBullets.setText("")
            checkFingerprintIcon()
            return super.onLongDeleteKeyPressed()
        }
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(context, attrs)
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs)
        init(context)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.PinScreen, 0, 0)
        try {
            vibrate = a.getBoolean(R.styleable.PinScreen_vibrate, false)
            showRecoverPin = a.getBoolean(R.styleable.PinScreen_recover_pin, false)
            showPinButton = a.getBoolean(R.styleable.PinScreen_show_pin, false)

        } finally {
            a.recycle()
        }
    }

    private fun init(context: Context) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ComponentPinWithKeyboardBinding.inflate(inflater, this, true)
        actions.vibrate = vibrate
        binding.componentPinScreenNumericKeyboard.setAction(actions)
        with(binding.componentPinScreenNumericKeyboard) {
            if (showRecoverPin) {
                showRecoverPin()
            } else {
                hideRecoverPin()
            }
        }
        binding.componentPinScreenBullets.showPinButtonVisibility(visible = showPinButton)
        checkFingerprintIcon()
    }

    private fun checkFingerprintIcon() {
        if (shouldShowFingerprintOption && binding.componentPinScreenBullets.getPin().isBlank()) {
            binding.componentPinScreenNumericKeyboard.setFingerprint()
        } else {
            binding.componentPinScreenNumericKeyboard.setBackspace()
        }
    }

    fun setPinValidator(listener: PinValidator){
        pinValidator = listener
    }

    fun setBiometricHelper(listener: BiometricHelper){
        biometricHelper = listener
        shouldShowFingerprintOption = listener.shouldShowFingerPrintScreen()
        checkFingerprintIcon()
    }

    fun setHeader(text: String) {
        binding.componentPinScreenHeader.text = text
    }

    fun setSubHeader(text: String) {
        binding.componentPinScreenSubheader.text = text
    }

    fun showError() {
        binding.componentPinScreenBullets.showErrorFeedback()
        binding.componentPinScreenBullets.setText("")
        binding.componentPinScreenSubheaderError.visible()
    }

    fun hideError() {
        binding.componentPinScreenSubheaderError.gone()
    }
    
    fun getPin(): String = binding.componentPinScreenBullets.getPin()
    
    fun isPinSet(): Boolean = binding.componentPinScreenBullets.isPinSet()

}