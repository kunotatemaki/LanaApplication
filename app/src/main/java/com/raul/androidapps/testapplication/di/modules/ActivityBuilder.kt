package com.raul.androidapps.testapplication.di.modules

import com.raul.androidapps.testapplication.di.interfaces.CustomScopes
import com.raul.androidapps.testapplication.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}