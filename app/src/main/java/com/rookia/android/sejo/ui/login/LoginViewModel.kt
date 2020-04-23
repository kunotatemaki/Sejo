package com.rookia.android.sejo.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.domain.local.user.TokenReceived
import com.rookia.android.sejo.usecases.LoginUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesManager: PreferencesManager,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val loginResult: MediatorLiveData<Result<TokenReceived>> = MediatorLiveData()
    private lateinit var _login: LiveData<Result<TokenReceived>>

    fun storeToken(token: String?) =
        loginUseCase.storeToken(token)

    fun login(
        phonePrefix: String,
        phoneNumber: String,
        pin: Int
    ) {
        _login = loginUseCase.login(phonePrefix, phoneNumber, pin).asLiveData(dispatcher)
        loginResult.addSource(_login) {
            loginResult.value = it
            if (it.status != Result.Status.LOADING) {
                loginResult.removeSource(_login)
            }
        }
    }


}