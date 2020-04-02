package com.rookia.android.androidutils.di.modules

import androidx.lifecycle.ViewModelProvider
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.framework.preferences.PreferencesManagerImpl
import com.rookia.android.androidutils.framework.resources.ResourcesManagerImpl
import com.rookia.android.androidutils.framework.utils.security.EncryptionImpl
import com.rookia.android.androidutils.framework.utils.view.ViewUtilsImpl
import com.rookia.android.androidutils.ui.common.ViewModelFactory
import com.rookia.android.androidutils.utils.RateLimiter
import com.rookia.android.androidutils.utils.ViewUtils
import com.rookia.android.androidutils.utils.security.Encryption
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class AndroidUtilsModule {


    @Binds
    @Singleton
    abstract fun providesResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager

    @Binds
    @Singleton
    abstract fun providesEncryption(encryptionImpl: EncryptionImpl): Encryption

    @Binds
    @Singleton
    abstract fun providesPreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager

    @Binds
    @Singleton
    abstract fun providesViewUtils(viewUtilsImpl: ViewUtilsImpl): ViewUtils

    @Singleton
    @Provides
    fun providesRateLimiter(): RateLimiter = RateLimiter()

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


}