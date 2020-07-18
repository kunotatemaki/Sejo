package com.rookia.android.sejo.ui.register.sms

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants.Navigation.PIN_SENT_TAG
import com.rookia.android.sejo.Constants.Navigation.VALIDATED_PHONE_TAG
import com.rookia.android.sejo.Constants.UserData.ID_TAG
import com.rookia.android.sejo.Constants.UserData.LAST_USED_GROUP_TAG
import com.rookia.android.sejo.Constants.UserData.PHONE_NUMBER_TAG
import com.rookia.android.sejo.Constants.UserData.PHONE_PREFIX_TAG
import com.rookia.android.sejo.di.modules.ProvidesModule
import com.rookia.android.sejo.domain.local.smscode.SmsCodeValidation
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver
import com.rookia.android.sejo.usecases.RequestSmsCodeUseCase
import com.rookia.android.sejo.usecases.ValidateSmsCodeUseCase
import kotlinx.coroutines.CoroutineDispatcher

class ValidateSmsViewModel @ViewModelInject constructor(
    private val smsCodeUseCase: RequestSmsCodeUseCase,
    private val validateCodeUseCase: ValidateSmsCodeUseCase,
    val receiver: SMSBroadcastReceiver,
    private val preferencesManager: PreferencesManager,
    @ProvidesModule.IODispatcher private val dispatcher: CoroutineDispatcher
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

    fun storeValidatedPhone(phonePrefix: String, phoneNumber: String) {
        with(preferencesManager){
            setBooleanIntoPreferences(VALIDATED_PHONE_TAG, true)
            setStringIntoPreferences(PHONE_PREFIX_TAG, phonePrefix)
            setStringIntoPreferences(PHONE_NUMBER_TAG, phoneNumber)
        }
    }

    fun setPinSet(userId: String, lastUsedGroup: Long?) {
        preferencesManager.setBooleanIntoPreferences(PIN_SENT_TAG, true)
        preferencesManager.setStringIntoPreferences(ID_TAG, userId)
        lastUsedGroup?.let {
            preferencesManager.setLongIntoPreferences(LAST_USED_GROUP_TAG, lastUsedGroup)
        }
    }


}