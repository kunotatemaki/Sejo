package com.rookia.android.sejo.di.modules

import com.rookia.android.sejo.data.persistence.PersistenceManager
import com.rookia.android.sejo.data.repository.*
import com.rookia.android.sejo.framework.persistence.PersistenceManagerImpl
import com.rookia.android.sejo.framework.repository.*
import dagger.Binds
import dagger.Module


@Module
abstract class BindsModule {

    @Binds
    abstract fun providesSmsCodeRepository(smsCodeRepositoryImpl: SmsCodeRepositoryImpl): SmsCodeRepository

    @Binds
    abstract fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun providesLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun providesContactsRepository(contactsRepository: ContactsRepositoryImpl): ContactsRepository

    @Binds
    abstract fun providesGroupRepository(groupRepository: GroupRepositoryImpl): GroupRepository

    @Binds
    abstract fun providesPersistenceManager(persistenceManagerImpl: PersistenceManagerImpl): PersistenceManager

}