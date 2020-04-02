package com.rookia.android.androidutils.di.modules

import com.rookia.android.androidutils.utils.RateLimiter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AndroidUtilsModule {

    @Singleton
    @Provides
    fun providesRateLimiter(): RateLimiter = RateLimiter()

}