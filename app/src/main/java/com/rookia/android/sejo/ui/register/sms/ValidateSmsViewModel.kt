package com.rookia.android.sejo.ui.register.sms

import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager

class ValidateSmsViewModel constructor(private val preferences: PreferencesManager) : ViewModel() {
    // TODO: Implement the ViewModel
    fun store(){
        preferences.setIntIntoPreferences("hola", 3)
    }

    fun getStore() = preferences.getIntFromPreferences("hola")
}