package com.raul.androidapps.lanaapplication.di.modules

import android.content.Context
import com.raul.androidapps.lanaapplication.MyApplication
import com.raul.androidapps.lanaapplication.persistence.databases.AppDatabase
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.ui.main.MainViewModel
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