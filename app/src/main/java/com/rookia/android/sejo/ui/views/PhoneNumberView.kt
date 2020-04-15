@file:Suppress("SameParameterValue")

package com.rookia.android.sejo.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.databinding.ComponentPhoneNumberBinding
import com.rookia.android.sejo.utils.PhoneUtils
import kotlin.math.min


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

class PhoneNumberView : ConstraintLayout {

    enum class PhoneNumberFormat {
        OK, INCOMPLETE, WRONG
    }

    private lateinit var binding: ComponentPhoneNumberBinding
    private val _validPhone: MutableLiveData<PhoneNumberFormat> =
        MutableLiveData(PhoneNumberFormat.INCOMPLETE)
    val isValidPhone: LiveData<PhoneNumberFormat> = _validPhone
    val phoneNumber: String
        get() = binding.componentPhoneNumber.text.toString()
    val phonePrefix: String
        get() = binding.componentPhonePrefix.selectedItem.toString()

    private val phoneUtils = PhoneUtils()

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
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ComponentPhoneNumberBinding.inflate(inflater, this, true)

        binding.componentPhoneNumber.addTextChangedListener(object : TextWatcher {
            var lastText = ""
            var deletingSpace = false
            override fun afterTextChanged(text: Editable?) {
                if (text?.toString() != lastText && text?.length ?: 0 > 3) {
                    val cleanPhoneNumber = text?.replace("[\\s]".toRegex(), "") ?: ""
                    var cursorPosition = binding.componentPhoneNumber.selectionStart
                    val cutText: CharSequence?
                    if (deletingSpace || cleanPhoneNumber.length > Constants.SPANISH_PHONE_NUMBER_MAX_LENGTH_WITHOUT_SPACES) {
                        cutText = text?.removeRange(cursorPosition - 1 until cursorPosition)
                        cursorPosition -= 1
                    } else {
                        cutText = text?.trim()
                    }
                    deletingSpace = false
                    lastText = phoneUtils.formatWithSpaces(phonePrefix, cutText.toString(), false)
                    binding.componentPhoneNumber.setText(lastText)
                    val cleanPhoneNumberAfter = cutText?.replace("[\\s]".toRegex(), "") ?: ""
                    val isValidPhone =
                        phoneUtils.isValidPhoneNumber(phonePrefix, cleanPhoneNumberAfter)
                    _validPhone.value = when {
                        isValidPhone -> PhoneNumberFormat.OK
                        isValidPhone.not() && cleanPhoneNumberAfter.length == Constants.SPANISH_PHONE_NUMBER_MAX_LENGTH_WITHOUT_SPACES -> PhoneNumberFormat.WRONG
                        else -> PhoneNumberFormat.INCOMPLETE
                    }

                    val spacesBefore =
                        text?.subSequence(0, cursorPosition)?.count { it == ' ' } ?: 0
                    val spacesAfter = lastText.subSequence(0, min(cursorPosition, lastText.length))
                        .count { it == ' ' }
                    cursorPosition += spacesAfter - spacesBefore
                    binding.componentPhoneNumber.setSelection(
                        min(
                            cursorPosition,
                            lastText.length
                        )
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (start < s?.length ?: 0 && after < count && s?.get(start) == ' ') {
                    deletingSpace = true
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    fun showError(message: String) {
        binding.componentError.apply {
            visibility = View.VISIBLE
            text = message
        }
    }

    fun hideError() {
        binding.componentError.apply {
            visibility = View.GONE
            text = null
        }
    }

}