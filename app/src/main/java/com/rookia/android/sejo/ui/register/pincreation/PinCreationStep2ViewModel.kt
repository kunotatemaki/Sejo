package com.rookia.android.sejo.ui.register.pincreation

import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class PinCreationStep2ViewModel @Inject constructor() : ViewModel() {

    fun validatePin(firstAttempt: String, secondAttempt: String) =
        firstAttempt == secondAttempt

    fun sendPinInfo() {
        Timber.d("")
    }
}
