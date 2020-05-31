package com.rookia.android.sejo.ui.register.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.domain.local.user.User
import com.rookia.android.sejo.usecases.UpdatePersonalInfoUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class PersonalInfoViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val userUseCase: UpdatePersonalInfoUseCase,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private lateinit var _userSentToServer: LiveData<Result<Int>>
    val userSentToServer: MediatorLiveData<Result<Boolean>> = MediatorLiveData()

    fun updatePersonalInfo(name: String) {
        try {
            val phonePrefix =
                preferencesManager.getStringFromPreferences(Constants.USER_PHONE_PREFIX_TAG)
                    ?: return
            val phoneNumber =
                preferencesManager.getStringFromPreferences(Constants.USER_PHONE_NUMBER_TAG)
                    ?: return
            val userId =
                preferencesManager.getStringFromPreferences(Constants.USER_ID_TAG)
                    ?: return
            val pushToken = preferencesManager.getStringFromPreferences(Constants.PUSH_TOKEN_TAG)
            val user = User(userId = userId, phonePrefix = phonePrefix, phoneNumber = phoneNumber,  name = name, pushToken = pushToken)
            _userSentToServer =
                userUseCase.updateUser(user).asLiveData(dispatcher)
            userSentToServer.addSource(_userSentToServer) {
                it?.let { result ->
                    userSentToServer.value =
                        Result.from(result.status, result.data == Constants.ResponseCodes.OK.code)
                }
                when(it.status){
                    Result.Status.SUCCESS -> {
                        userSentToServer.removeSource(_userSentToServer)
                        preferencesManager.setStringIntoPreferences(Constants.USER_NAME_TAG, name)
                        preferencesManager.setBooleanIntoPreferences(Constants.NAVIGATION_PERSONAL_INFO_TAG, true)
                    }
                    Result.Status.ERROR -> userSentToServer.removeSource(_userSentToServer)
                    Result.Status.LOADING -> {}
                }
            }
        } catch (e: NumberFormatException) {
        }
    }

}
