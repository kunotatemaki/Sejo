package com.rookia.android.sejo.ui.register.pincreation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.di.modules.ProvidesModule
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.usecases.CreateUserUseCase
import kotlinx.coroutines.CoroutineDispatcher

class PinCreationStep2ViewModel @ViewModelInject constructor(
    private val preferencesManager: PreferencesManager,
    private val userUseCase: CreateUserUseCase,
    @ProvidesModule.IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private lateinit var _pinSentToServer: LiveData<Result<TokenReceived>>
    val pinSentToServer: MediatorLiveData<Result<Boolean>> = MediatorLiveData()

    fun validatePin(firstAttempt: String, secondAttempt: String) =
        firstAttempt == secondAttempt

    fun sendPinInfo(pin: String) {
        try {
            val nPin = pin.toInt()
            val phonePrefix =
                preferencesManager.getStringFromPreferences(Constants.UserData.PHONE_PREFIX_TAG)
                    ?: return
            val phoneNumber =
                preferencesManager.getStringFromPreferences(Constants.UserData.PHONE_NUMBER_TAG)
                    ?: return
            _pinSentToServer =
                userUseCase.createUser(phonePrefix, phoneNumber, nPin).asLiveData(dispatcher)
            pinSentToServer.addSource(_pinSentToServer) {
                it?.let { result ->
                    pinSentToServer.value =
                        Result.from(result.status, result.data != null)
                }
                when(it.status){
                    Result.Status.SUCCESS -> {
                        pinSentToServer.removeSource(_pinSentToServer)
                        preferencesManager.setEncryptedStringIntoPreferences(Constants.UserData.PIN_TAG, pin, Constants.UserData.PIN_ALIAS)
                        preferencesManager.setBooleanIntoPreferences(Constants.Navigation.PIN_SENT_TAG, true)
                        it.data?.let { tokenReceived ->
                            preferencesManager.setStringIntoPreferences(Constants.UserData.ID_TAG, tokenReceived.userId)
                        }
                    }
                    Result.Status.ERROR -> pinSentToServer.removeSource(_pinSentToServer)
                    Result.Status.LOADING -> {}
                }
            }
        } catch (e: NumberFormatException) {
        }
    }
}
