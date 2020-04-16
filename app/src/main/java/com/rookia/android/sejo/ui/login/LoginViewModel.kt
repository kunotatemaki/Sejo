package com.rookia.android.sejo.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.domain.local.SmsCodeValidation
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver
import com.rookia.android.sejo.usecases.RequestSmsCodeUseCase
import com.rookia.android.sejo.usecases.ValidateSmsCodeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class LoginViewModel @Inject constructor(
    private val smsCodeUseCase: RequestSmsCodeUseCase,
    private val validateCodeUseCase: ValidateSmsCodeUseCase,
    val receiver: SMSBroadcastReceiver,
    private val preferencesManager: PreferencesManager,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val smsCodeValidationResult: MediatorLiveData<Result<SmsCodeValidation>> = MediatorLiveData()
    private lateinit var _smsCodeValidation: LiveData<Result<SmsCodeValidation>>

    fun requestSms(phonePrefix: String, phoneNumber: String): LiveData<Result<Int>> =
        smsCodeUseCase.askForSmsCode(phonePrefix, phoneNumber).asLiveData(dispatcher)

    fun validateCode(
        phonePrefix: String,
        phoneNumber: String,
        smsCode: String
    ) {
        _smsCodeValidation = validateCodeUseCase.validateSmsCode(phonePrefix, phoneNumber, smsCode)
            .asLiveData(dispatcher)
        smsCodeValidationResult.addSource(_smsCodeValidation) {
            smsCodeValidationResult.value = _smsCodeValidation.value
            if (it.status != Result.Status.LOADING) {
                smsCodeValidationResult.removeSource(_smsCodeValidation)
            }
        }
    }


}