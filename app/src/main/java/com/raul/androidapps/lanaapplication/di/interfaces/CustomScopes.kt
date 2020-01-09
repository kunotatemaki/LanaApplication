package com.raul.androidapps.lanaapplication.di.interfaces

import javax.inject.Scope


interface CustomScopes {

    @Scope
    annotation class ActivityScope

    @Scope
    annotation class FragmentScope

}