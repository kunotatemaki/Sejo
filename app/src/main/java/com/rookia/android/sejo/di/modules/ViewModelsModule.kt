package com.rookia.android.sejo.di.modules

import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.di.interfaces.ViewModelKey
import com.rookia.android.sejo.ui.register.number.ValidatePhoneViewModel
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
    @ViewModelKey(ValidatePhoneViewModel::class)
    internal abstract fun bindValidatePhoneViewModel(validatePhoneViewModel: ValidatePhoneViewModel): ViewModel
}
