package com.rookia.android.sejo.ui.register.pincreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants
import javax.inject.Inject

class PinCreationStep2ViewModel @Inject constructor(private val preferencesManager: PreferencesManager) : ViewModel() {

    private val _pinSentToServer: MutableLiveData<Boolean> = MutableLiveData(false)
    val pinSentToServer: LiveData<Boolean> = _pinSentToServer

    fun validatePin(firstAttempt: String, secondAttempt: String) =
        firstAttempt == secondAttempt

    fun sendPinInfo() {
        preferencesManager.setBooleanIntoPreferences(Constants.NAVIGATION_PIN_SENT_TAG, true)
        _pinSentToServer.value = true
    }
}
