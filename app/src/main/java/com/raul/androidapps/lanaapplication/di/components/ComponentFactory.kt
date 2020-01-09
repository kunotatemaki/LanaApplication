package com.raul.androidapps.lanaapplication.di.components

import com.raul.androidapps.lanaapplication.MyApplication


object ComponentFactory {

    fun component(context: MyApplication): AppComponent {
        return DaggerAppComponent.builder()
                .application(context)
                .build()
    }

}