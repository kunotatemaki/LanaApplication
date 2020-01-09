package com.raul.androidapps.testapplication.di.modules

import android.content.Context
import com.raul.androidapps.testapplication.MyApplication
import com.raul.androidapps.testapplication.di.interfaces.CustomScopes
import com.raul.androidapps.testapplication.persistence.databases.AppDatabase
import com.raul.androidapps.testapplication.repository.Repository
import com.raul.androidapps.testapplication.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class ProvidesModule {


    @Provides
    fun providesContext(application: MyApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideDb(
        context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideMainViewModel(repository: Repository): MainViewModel = MainViewModel(repository)


}