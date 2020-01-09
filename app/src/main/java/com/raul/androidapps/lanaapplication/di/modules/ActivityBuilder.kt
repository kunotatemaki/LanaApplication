package com.raul.androidapps.lanaapplication.di.modules

import com.raul.androidapps.lanaapplication.di.interfaces.CustomScopes
import com.raul.androidapps.lanaapplication.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}