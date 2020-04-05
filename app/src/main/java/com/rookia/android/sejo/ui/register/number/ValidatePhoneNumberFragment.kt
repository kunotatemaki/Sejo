package com.rookia.android.sejo.ui.register.number

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.ValidateNumberFragmentBinding
import com.rookia.android.sejo.utils.PhoneUtils
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.min

class ValidatePhoneNumberFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory,
    private val phoneUtils: PhoneUtils
) : Fragment(R.layout.validate_number_fragment) {

    private lateinit var binding: ValidateNumberFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ValidateNumberFragmentBinding.bind(view)
        binding.validatePhoneNumberContinueButton.setOnClickListener { navigateToSmsValidation() }
        binding.validatePhoneNumberPhoneNumber.addTextChangedListener(object : TextWatcher {
            var lastText = ""
            var deletingSpace = false
            override fun afterTextChanged(text: Editable?) {
                //todo leer el prefijo
                if (text?.toString() != lastText && text?.length ?: 0 > 3) {
                    val cleanPhoneNumber = text?.replace("[\\s]".toRegex(), "") ?: ""
                    var cursorPosition = binding.validatePhoneNumberPhoneNumber.selectionStart
                    val cutText: CharSequence?
                    if (deletingSpace || cleanPhoneNumber.length > Constants.SPANISH_PHONE_NUMBER_MAX_LENGTH_WITHOUT_SPACES) {
                        cutText = text?.removeRange(cursorPosition-1 until cursorPosition)
                        cursorPosition -= 1
                    } else {
                        cutText = text?.trim()
                    }
                    deletingSpace = false
                    lastText = phoneUtils.formatWithSpaces("+34", cutText.toString(), false)
                        binding.validatePhoneNumberPhoneNumber.setText(lastText)
                    val cleanPhoneNumberAfter = cutText?.replace("[\\s]".toRegex(), "") ?: ""
                        binding.buttonEnabled = phoneUtils.isValidPhoneNumber("+34", cleanPhoneNumberAfter)

                    val spacesBefore = text?.subSequence(0, cursorPosition)?.count { it ==  ' '} ?: 0
                    val spacesAfter = lastText.subSequence(0, min(cursorPosition, lastText.length)).count { it ==  ' '}
                    cursorPosition += spacesAfter - spacesBefore
                        binding.validatePhoneNumberPhoneNumber.setSelection(min(cursorPosition, lastText.length))
                }
            }


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(after == 0 && s?.get(start) == ' '){
                    deletingSpace = true
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    private fun navigateToSmsValidation() {
        findNavController().navigate(ValidatePhoneNumberFragmentDirections.actionValidateNumberFragmentToValidateSmsFragment())
    }

}