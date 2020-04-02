package com.rookia.android.sejo.di.modules

import android.content.Context
import com.rookia.android.sejo.SejoApplication
import dagger.Module
import dagger.Provides

@Module
class ProvidesModule {

    @Provides
    fun providesContext(application: SejoApplication): Context =
        application.applicationContext

//    @Singleton
//    @Provides
//    fun provideDb(
//        context: Context
//    ): AppDatabase = AppDatabase.getInstance(context)


}