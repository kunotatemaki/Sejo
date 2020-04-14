package com.rookia.android.sejo.ui.register.number

import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.Constants.HAS_VALIDATED_PHONE_TAG
import javax.inject.Inject

class ValidatePhoneViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    fun isPhoneValidated(): Boolean =true
//        preferencesManager.getBooleanFromPreferences(HAS_VALIDATED_PHONE_TAG)
}