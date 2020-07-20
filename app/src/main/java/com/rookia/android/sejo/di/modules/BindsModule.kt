package com.rookia.android.sejo.di.modules

import com.rookia.android.sejo.data.repository.UserRepository
import com.rookia.android.sejo.framework.repository.UserRepositoryImpl
import com.rookia.android.sejoandroidframework.data.datasources.*
import com.rookia.android.sejocore.data.local.ContactsLocalDataSource
import com.rookia.android.sejocore.data.local.GroupsLocalDataSource
import com.rookia.android.sejocore.data.remote.GroupsRemoteDataSource
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
    abstract fun providesContactsLocalDataSource(contactsLocalDataSourceImpl: ContactsLocalDataSourceImpl): ContactsLocalDataSource

    @Binds
    abstract fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun providesGroupsLocalDataSource(groupsLocalDataSourceImpl: GroupsLocalDataSourceImp): GroupsLocalDataSource

    @Binds
    abstract fun providesGroupsRemoteDataSource(groupsRemoteDataSourceImpl: GroupsRemoteDataSourceImpl): GroupsRemoteDataSource


}