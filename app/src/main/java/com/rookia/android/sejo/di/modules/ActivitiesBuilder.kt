package com.rookia.android.sejo.di.modules

import com.rookia.android.androidutils.di.interfaces.CustomScopes
import com.rookia.android.sejo.ui.common.MainActivity
import com.rookia.android.sejo.ui.register.RegisterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivitiesBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindRegisterActivity(): RegisterActivity


}