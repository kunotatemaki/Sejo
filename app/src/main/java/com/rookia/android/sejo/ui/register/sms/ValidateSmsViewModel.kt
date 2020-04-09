package com.rookia.android.sejo.ui.register.sms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver

class ValidateSmsViewModel constructor(private val preferencesManager: PreferencesManager) : ViewModel() {

    private val _code:  MutableLiveData<String> = MutableLiveData()
    val code: LiveData<String> = _code
    val receiver = SMSBroadcastReceiver(_code)

    fun requestSms(phonePrefix: String?, phoneNumber: String?) {
        if(phonePrefix == null || phoneNumber == null){
            return
        }

    }

}