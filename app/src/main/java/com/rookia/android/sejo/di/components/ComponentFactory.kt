package com.rookia.android.sejo.di.components

import com.rookia.android.sejo.SejoApplication


object ComponentFactory {

    fun component(context: SejoApplication): AppComponent {
        return DaggerAppComponent.builder()
                .application(context)
                .build()
    }

}