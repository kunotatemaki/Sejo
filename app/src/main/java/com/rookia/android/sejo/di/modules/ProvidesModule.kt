package com.rookia.android.sejo.di.modules

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.MutableLiveData
import androidx.navigation.ui.AppBarConfiguration
import com.rookia.android.sejo.R
import com.rookia.android.sejo.framework.persistence.databases.AppDatabase
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver
import com.rookia.android.sejo.utils.TextFormatUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ProvidesModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SmsCode


    @Provides
    @SmsCode
    @Singleton
    fun providesSmsCodeObservable(): MutableLiveData<String> = MutableLiveData()

    @Provides
    fun providesSMSBroadcastReceiver(
        @SmsCode code: MutableLiveData<String>,
        textFormatUtils: TextFormatUtils
    ): SMSBroadcastReceiver =
        SMSBroadcastReceiver(code, textFormatUtils)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class IODispatcher

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class MainDispatcher

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DefaultDispatcher

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @IODispatcher
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun providesBiometricPromptInfoDialog(@ApplicationContext context: Context): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.fragment_login_with_fingerprint_text))
            .setNegativeButtonText(context.getString(R.string.fragment_login_with_fingerprint_use_pin_text))
            .build()

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun providesAppBarConfiguration(): AppBarConfiguration =
        AppBarConfiguration(
            setOf(
                R.id.pinCreationStep1Fragment,
                R.id.loginFragment,
                R.id.biometricPermissionFragment,
                R.id.groupsFragment,
                R.id.moreFragment
            )
        )

}