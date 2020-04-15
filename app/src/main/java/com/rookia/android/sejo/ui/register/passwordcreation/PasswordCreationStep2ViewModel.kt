package com.rookia.android.sejo.ui.register.passwordcreation

import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class PasswordCreationStep2ViewModel @Inject constructor() : ViewModel() {

    fun validatePassword(firstAttempt: String, secondAttempt: String) =
        firstAttempt == secondAttempt

    fun sendPasswordInfo() {
        Timber.d("")
    }
}
