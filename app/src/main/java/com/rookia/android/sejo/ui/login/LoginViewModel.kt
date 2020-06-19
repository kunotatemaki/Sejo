package com.rookia.android.sejo.ui.login

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
import com.rookia.android.sejo.usecases.LoginUseCase
import kotlinx.coroutines.CoroutineDispatcher

class LoginViewModel @ViewModelInject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginStatus: LoginStatus,
    private val preferencesManager: PreferencesManager,
    @ProvidesModule.IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val loginResult: MediatorLiveData<Result<TokenReceived>> = MediatorLiveData()
    private lateinit var _login: LiveData<Result<TokenReceived>>

    fun login(
        pin: Int
    ) {
        val userId = preferencesManager.getStringFromPreferences(Constants.USER_DATA.ID_TAG) ?: ""
        val pushToken = preferencesManager.getStringFromPreferences(Constants.USER_DATA.PUSH_TOKEN_TAG) ?: ""
        _login = loginUseCase.login(userId, pin, pushToken).asLiveData(dispatcher)
        loginResult.addSource(_login) {
            loginResult.value = it
            when (it.status) {
                Result.Status.SUCCESS -> {
                    preferencesManager.setEncryptedStringIntoPreferences(Constants.USER_DATA.PIN_TAG, pin.toString(), Constants.USER_DATA.PIN_ALIAS)
                    loginResult.removeSource(_login)
                    loginStatus.avoidGoingToLogin()
                }
                Result.Status.ERROR -> loginResult.removeSource(_login)
                Result.Status.LOADING -> {
                }
            }
            if (it.status != Result.Status.LOADING) {
                loginResult.removeSource(_login)
            }
        }
    }

}