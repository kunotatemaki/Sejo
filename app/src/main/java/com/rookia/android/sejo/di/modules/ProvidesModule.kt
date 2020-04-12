package com.rookia.android.sejo.di.modules

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.SejoApplication
import com.rookia.android.sejo.data.repository.SmsCodeRepository
import com.rookia.android.sejo.framework.network.NetworkServiceFactory
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver
import com.rookia.android.sejo.ui.register.sms.ValidateSmsViewModel
import com.rookia.android.sejo.usecases.RequestSmsCodeUseCase
import com.rookia.android.sejo.usecases.ValidateSmsCodeUseCase
import com.rookia.android.sejo.utils.TextFormatUtils
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ProvidesModule {

    @Provides
    fun providesContext(application: SejoApplication): Context =
        application.applicationContext

    @Provides
    fun providesValidateSmsViewModel(
        requestSmsCodeUseCase: RequestSmsCodeUseCase,
        validateSmsCodeUseCase: ValidateSmsCodeUseCase,
        receiver: SMSBroadcastReceiver,
        preferencesManager: PreferencesManager
    ): ValidateSmsViewModel =
        ValidateSmsViewModel(requestSmsCodeUseCase, validateSmsCodeUseCase, receiver, preferencesManager)

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

    @Provides
    fun providesRequestSmsCodeUseCase(repository: SmsCodeRepository): RequestSmsCodeUseCase =
        RequestSmsCodeUseCase(repository)

    @Provides
    fun providesValidateSmsCodeUseCase(repository: SmsCodeRepository): ValidateSmsCodeUseCase =
        ValidateSmsCodeUseCase(repository)

    @Singleton
    @Provides
    fun provideNetworkServiceFactory(): NetworkServiceFactory = NetworkServiceFactory()

//    @Singleton
//    @Provides
//    fun provideDb(
//        context: Context
//    ): AppDatabase = AppDatabase.getInstance(context)


}