package com.raul.androidapps.testapplication.di.components

import com.raul.androidapps.testapplication.MyApplication


object ComponentFactory {

    fun component(context: MyApplication): AppComponent {
        return DaggerAppComponent.builder()
                .application(context)
                .build()
    }

}