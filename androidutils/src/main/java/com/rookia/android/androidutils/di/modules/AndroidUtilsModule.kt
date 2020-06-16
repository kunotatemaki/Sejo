package com.rookia.android.androidutils.di.modules

import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.data.resources.ResourcesManager
import com.rookia.android.androidutils.framework.preferences.PreferencesManagerImpl
import com.rookia.android.androidutils.framework.resources.ResourcesManagerImpl
import com.rookia.android.androidutils.framework.utils.security.EncryptionImpl
import com.rookia.android.androidutils.framework.utils.view.ViewUtilsImpl
import com.rookia.android.androidutils.utils.ViewUtils
import com.rookia.android.androidutils.utils.security.Encryption
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class AndroidUtilsModule {

    @Binds
    abstract fun providesResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager

    @Binds
    abstract fun providesEncryption(encryptionImpl: EncryptionImpl): Encryption

    @Binds
    abstract fun providesPreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager

    @Binds
    abstract fun providesViewUtils(viewUtilsImpl: ViewUtilsImpl): ViewUtils

}