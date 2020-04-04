package com.rookia.android.sejo.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.sejo.SejoApplication
import com.rookia.android.sejo.ui.register.sms.ValidateSmsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ProvidesModule {

    @Provides
    fun providesContext(application: SejoApplication): Context =
        application.applicationContext

    @Provides
    fun providesValidateSmsViewModel(preferencesManager: PreferencesManager): ValidateSmsViewModel =
        ValidateSmsViewModel(preferencesManager)




//    @Singleton
//    @Provides
//    fun provideDb(
//        context: Context
//    ): AppDatabase = AppDatabase.getInstance(context)


}