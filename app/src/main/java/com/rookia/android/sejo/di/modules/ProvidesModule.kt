package com.rookia.android.sejo.di.modules

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.MutableLiveData
import androidx.navigation.ui.AppBarConfiguration
import com.rookia.android.kotlinutils.utils.RateLimiter
import com.rookia.android.sejo.R
import com.rookia.android.sejo.framework.receivers.SMSBroadcastReceiver
import com.rookia.android.sejo.utils.TextFormatUtils
import com.rookia.android.sejocore.data.local.ContactsLocalDataSource
import com.rookia.android.sejocore.data.local.GroupsLocalDataSource
import com.rookia.android.sejocore.data.local.UserLocalDataSource
import com.rookia.android.sejocore.data.remote.GroupsRemoteDataSource
import com.rookia.android.sejocore.data.remote.LoginRemoteDataSource
import com.rookia.android.sejocore.data.remote.SmsCodeRemoteDataSource
import com.rookia.android.sejocore.data.remote.UserRemoteDataSource
import com.rookia.android.sejocore.data.repository.*
import com.rookia.android.sejocoreandroid.data.persistence.databases.AppDatabase
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
                R.id.selectedGroupFragment,
                R.id.paymentsFragment,
                R.id.membersFragment,
                R.id.moreFragment
            )
        )

    @Provides
    fun providesSmsCodeRepository(smsCodeRemoteDataSource: SmsCodeRemoteDataSource): SmsCodeRepository =
        SmsCodeRepository(smsCodeRemoteDataSource)

    @Provides
    fun providesContactsRepository(contactsLocalDataSource: ContactsLocalDataSource): ContactsRepository =
        ContactsRepository(contactsLocalDataSource)

    @Provides
    fun providesLoginRepository(loginLocalDataSource: LoginRemoteDataSource): LoginRepository =
        LoginRepository(loginLocalDataSource)

    @Provides
    fun providesGroupsRepository(
        groupsRemoteDataSource: GroupsRemoteDataSource,
        groupsLocalDataSource: GroupsLocalDataSource,
        rateLimiter: RateLimiter
    ): GroupsRepository =
        GroupsRepository(
            groupsLocalDataSource = groupsLocalDataSource,
            groupsRemoteDataSource = groupsRemoteDataSource,
            rateLimiter = rateLimiter
        )

    @Provides
    fun providesUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository = UserRepository(userRemoteDataSource, userLocalDataSource)

    @Provides
    fun providesRateLimiter(): RateLimiter = RateLimiter()

}