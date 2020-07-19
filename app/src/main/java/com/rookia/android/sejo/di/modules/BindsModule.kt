package com.rookia.android.sejo.di.modules

import com.rookia.android.sejo.data.persistence.PersistenceManager
import com.rookia.android.sejo.data.repository.ContactsRepository
import com.rookia.android.sejo.data.repository.GroupRepository
import com.rookia.android.sejo.data.repository.UserRepository
import com.rookia.android.sejo.framework.persistence.PersistenceManagerImpl
import com.rookia.android.sejo.framework.repository.ContactsRepositoryImpl
import com.rookia.android.sejo.framework.repository.GroupRepositoryImpl
import com.rookia.android.sejo.framework.repository.UserRepositoryImpl
import com.rookia.android.sejoandroidframework.data.datasources.LoginRemoteDataSourceImpl
import com.rookia.android.sejoandroidframework.data.datasources.SmsCodeRemoteDataSourceImpl
import com.rookia.android.sejocore.data.remote.LoginRemoteDataSource
import com.rookia.android.sejocore.data.remote.SmsCodeRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun providesSmsCodeRemoteDataSource(smsCodeRemoteDataSourceImpl: SmsCodeRemoteDataSourceImpl): SmsCodeRemoteDataSource

    @Binds
    abstract fun providesLoginRemoteDataSource(loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource

    @Binds
    abstract fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun providesContactsRepository(contactsRepository: ContactsRepositoryImpl): ContactsRepository

    @Binds
    abstract fun providesGroupRepository(groupRepository: GroupRepositoryImpl): GroupRepository

    @Binds
    abstract fun providesPersistenceManager(persistenceManagerImpl: PersistenceManagerImpl): PersistenceManager

}