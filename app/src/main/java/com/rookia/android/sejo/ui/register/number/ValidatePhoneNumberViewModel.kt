package com.rookia.android.sejo.ui.register.number

import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager

class ValidatePhoneNumberViewModel constructor(private val preferences: PreferencesManager) : ViewModel() {
    fun store(){
        preferences.setIntIntoPreferences("hola", 3)
    }

    fun getStore() = preferences.getIntFromPreferences("hola")
}