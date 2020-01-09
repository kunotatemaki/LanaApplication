package com.raul.androidapps.testapplication.di.modules

import com.raul.androidapps.testapplication.di.interfaces.CustomScopes
import com.raul.androidapps.testapplication.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Suppress("unused")
@Module
abstract class FragmentsProvider {

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesMainFragmentFactory(): MainFragment

}