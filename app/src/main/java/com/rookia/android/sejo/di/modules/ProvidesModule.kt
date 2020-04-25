package com.rookia.android.sejo.di.modules

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.MutableLiveData
import com.rookia.android.sejo.R
import com.rookia.android.sejo.SejoApplication
import com.rookia.android.sejo.data.repository.ContactsRepository
import com.rookia.android.sejo.data.repository.SmsCodeRepository
import com.rookia.android.sejo.data.repository.UserRepository
import com.rookia.android.sejo.framework.network.NetworkServiceFactory
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver
import com.rookia.android.sejo.ui.login.LoginStatus
import com.rookia.android.sejo.usecases.*
import com.rookia.android.sejo.utils.TextFormatUtils
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
class ProvidesModule {

    @Provides
    fun providesContext(application: SejoApplication): Context =
        application.applicationContext

    @Provides
    @Named("smsCode")
    @Singleton
    fun providesSmsCodeObservable(): MutableLiveData<String> = MutableLiveData()

    @Provides
    fun providesSMSBroadcastReceiver(
        @Named("smsCode") code: MutableLiveData<String>,
        textFormatUtils: TextFormatUtils
    ): SMSBroadcastReceiver =
        SMSBroadcastReceiver(code, textFormatUtils)

    @Singleton
    @Provides
    fun providesRequestSmsCodeUseCase(repository: SmsCodeRepository): RequestSmsCodeUseCase =
        RequestSmsCodeUseCase(repository)

    @Singleton
    @Provides
    fun providesValidateSmsCodeUseCase(repository: SmsCodeRepository): ValidateSmsCodeUseCase =
        ValidateSmsCodeUseCase(repository)

    @Singleton
    @Provides
    fun providesCreateUserUseCase(repository: UserRepository): CreateUserUseCase =
        CreateUserUseCase(repository)

    @Singleton
    @Provides
    fun providesLoginUseCase(repository: UserRepository): LoginUseCase =
        LoginUseCase(repository)

    @Provides
    fun providesUpdatePersonalInfoUseCase(repository: UserRepository): UpdatePersonalInfoUseCase =
        UpdatePersonalInfoUseCase(repository)

    @Provides
    fun providesGetContactsUseCase(repository: ContactsRepository): GetContactsUseCase =
        GetContactsUseCase(repository)

    @Singleton
    @Provides
    fun provideNetworkServiceFactory(): NetworkServiceFactory = NetworkServiceFactory()

    @Provides
    @Named("Main")
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Named("IO")
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named("Default")
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    fun providesBiometricPromptInfoDialog(context: Context): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.fragment_login_with_fingerprint_text))
            .setNegativeButtonText(context.getString(R.string.fragment_login_with_fingerprint_use_pin_text))
            .build()

    @Provides
    @Singleton
    fun providesLoginStatus(application: SejoApplication): LoginStatus = application.loginStatus

}