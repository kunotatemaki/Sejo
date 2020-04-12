package com.rookia.android.sejo.di.modules

import com.rookia.android.sejo.data.repository.SmsCodeRepository
import com.rookia.android.sejo.framework.repository.SmsCodeRepositoryImpl
import dagger.Binds
import dagger.Module


@Module
abstract class BindsModule {

    @Binds
    abstract fun providesSmsCodeRepository(smsCodeRepositoryImpl: SmsCodeRepositoryImpl): SmsCodeRepository

//    @Binds
//    abstract fun providesRepository(repositoryImpl: RepositoryImpl): Repository


}