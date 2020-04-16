package com.rookia.android.sejo.ui.register.pincreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class PinCreationStep2ViewModel @Inject constructor() : ViewModel() {

    private val _pinSentToServer: MutableLiveData<Boolean> = MutableLiveData(false)
    val pinSentToServer: LiveData<Boolean> = _pinSentToServer

    fun validatePin(firstAttempt: String, secondAttempt: String) =
        firstAttempt == secondAttempt

    fun sendPinInfo() {
        _pinSentToServer.value = true
    }
}
