package com.rookia.android.sejo.di.components

import com.rookia.android.androidutils.di.modules.AndroidUtilsBindsModule
import com.rookia.android.androidutils.di.modules.AndroidUtilsModule
import com.rookia.android.sejo.SejoApplication
import com.rookia.android.sejo.di.modules.ActivitiesBuilder
import com.rookia.android.sejo.di.modules.BindsModule
import com.rookia.android.sejo.di.modules.FragmentsProvider
import com.rookia.android.sejo.di.modules.ProvidesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (ActivitiesBuilder::class),
        (BindsModule::class),
        (ProvidesModule::class),
        (FragmentsProvider::class),
        (FragmentsProvider::class),
        (AndroidUtilsModule::class),
        (AndroidUtilsBindsModule::class)
    ]
)
interface AppComponent : AndroidInjector<SejoApplication> {

    override fun inject(goTFlightsApp: SejoApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: SejoApplication): Builder

        fun build(): AppComponent
    }

}