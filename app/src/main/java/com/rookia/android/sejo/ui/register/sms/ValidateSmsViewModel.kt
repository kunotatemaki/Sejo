package com.rookia.android.sejo.ui.register.sms

import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager

class ValidateSmsViewModel constructor(private val preferencesManager: PreferencesManager) : ViewModel() {

    fun requestSms(phonePrefix: String?, phoneNumber: String?) {
        if(phonePrefix == null || phoneNumber == null){
            return
        }

    }
}