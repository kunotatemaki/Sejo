package com.rookia.android.sejo.ui.register.pincreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.usecases.CreateUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class PinCreationStep2ViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val userUseCase: CreateUserUseCase,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private lateinit var _pinSentToServer: LiveData<Result<Int>>
    val pinSentToServer: MediatorLiveData<Result<Boolean>> = MediatorLiveData()

    fun validatePin(firstAttempt: String, secondAttempt: String) =
        firstAttempt == secondAttempt

    fun sendPinInfo(pin: String) {
        try {
            val nPin = pin.toInt()
            val phonePrefix =
                preferencesManager.getStringFromPreferences(Constants.USER_PHONE_PREFIX_TAG)
                    ?: return
            val phoneNumber =
                preferencesManager.getStringFromPreferences(Constants.USER_PHONE_NUMBER_TAG)
                    ?: return
            _pinSentToServer =
                userUseCase.createUSer(phonePrefix, phoneNumber, nPin).asLiveData(dispatcher)
            pinSentToServer.addSource(_pinSentToServer) {
                it?.let { result ->
                    pinSentToServer.value =
                        Result.from(result.status, result.data == Constants.GENERAL_RESPONSE_OK)
                }
                when(it.status){
                    Result.Status.SUCCESS -> {
                        pinSentToServer.removeSource(_pinSentToServer)
                        preferencesManager.setEncryptedStringIntoPreferences(Constants.USER_PIN_TAG, pin, Constants.USER_PIN_ALIAS)
                    }
                    Result.Status.ERROR -> pinSentToServer.removeSource(_pinSentToServer)
                    Result.Status.LOADING -> {}
                }
            }
        } catch (e: NumberFormatException) {
        }
    }

    fun storePinCreated() {
        preferencesManager.setBooleanIntoPreferences(Constants.NAVIGATION_PIN_SENT_TAG, true)
    }
}
