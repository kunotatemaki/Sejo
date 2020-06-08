package com.rookia.android.sejo.di.modules

import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.di.interfaces.ViewModelKey
import com.rookia.android.sejo.ui.dashboard.MembersViewModel
import com.rookia.android.sejo.ui.dashboard.PaymentsViewModel
import com.rookia.android.sejo.ui.groupcreation.GroupCreationMembersViewModel
import com.rookia.android.sejo.ui.login.LoginViewModel
import com.rookia.android.sejo.ui.main.MainViewModel
import com.rookia.android.sejo.ui.register.name.PersonalInfoViewModel
import com.rookia.android.sejo.ui.register.pincreation.PinCreationStep2ViewModel
import com.rookia.android.sejo.ui.register.sms.ValidateSmsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Copyright (C) Rookia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Roll <raulfeliz@gmail.com>, April 2020
 *
 *
 */
@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ValidateSmsViewModel::class)
    internal abstract fun bindValidateSmsViewModel(validateSmsViewModel: ValidateSmsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonalInfoViewModel::class)
    internal abstract fun bindPersonalInfoViewModel(personalInfoViewModel: PersonalInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GroupCreationMembersViewModel::class)
    internal abstract fun bindGroupMembersViewModel(groupCreationMembersViewModel: GroupCreationMembersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinCreationStep2ViewModel::class)
    internal abstract fun bindPinCreationStep2ViewModel(pinCreationStep2ViewModel: PinCreationStep2ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentsViewModel::class)
    internal abstract fun bindPaymentsViewModel(paymentsViewModel: PaymentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MembersViewModel::class)
    internal abstract fun bindMembersViewModel(membersViewModel: MembersViewModel): ViewModel
}
