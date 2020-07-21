package com.rookia.android.sejo.di.modules

import com.rookia.android.sejocore.data.local.ContactsLocalDataSource
import com.rookia.android.sejocore.data.local.GroupsLocalDataSource
import com.rookia.android.sejocore.data.local.UserLocalDataSource
import com.rookia.android.sejocore.data.remote.GroupsRemoteDataSource
import com.rookia.android.sejocore.data.remote.LoginRemoteDataSource
import com.rookia.android.sejocore.data.remote.SmsCodeRemoteDataSource
import com.rookia.android.sejocore.data.remote.UserRemoteDataSource
import com.rookia.android.sejocoreandroid.data.datasources.*
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
    abstract fun providesUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    abstract fun providesUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun providesGroupsLocalDataSource(groupsLocalDataSourceImpl: GroupsLocalDataSourceImp): GroupsLocalDataSource

    @Binds
    abstract fun providesGroupsRemoteDataSource(groupsRemoteDataSourceImpl: GroupsRemoteDataSourceImpl): GroupsRemoteDataSource


}