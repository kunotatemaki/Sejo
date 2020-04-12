package com.rookia.android.sejo.ui.register.sms

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.RequestSmsCodeUseCase
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver

class ValidateSmsViewModel constructor(
    private val smsCodeUseCase: RequestSmsCodeUseCase,
    val receiver: SMSBroadcastReceiver
) : ViewModel() {

    fun requestSms(phonePrefix: String, phoneNumber: String): LiveData<Result<Int>>
         = smsCodeUseCase.askForSmsCode(phonePrefix, phoneNumber)


}