package com.raul.androidapps.lanaapplication.di.modules

import android.content.Context
import com.raul.androidapps.lanaapplication.MyApplication
import com.raul.androidapps.lanaapplication.persistence.databases.AppDatabase
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.ui.checkout.CheckoutViewModel
import com.raul.androidapps.lanaapplication.ui.main.ProductsViewModel
import com.raul.androidapps.lanaapplication.utils.RateLimiter
import com.raul.androidapps.lanaapplication.utils.ViewUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class ProvidesModule {

    @Singleton
    @Provides
    fun providesRateLimiter(): RateLimiter = RateLimiter()

    @Provides
    fun providesContext(application: MyApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideDb(
        context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideMainViewModel(repository: Repository): ProductsViewModel = ProductsViewModel(repository)

    @Provides
    fun provideCheckoutViewModel(repository: Repository, resourcesManager: ResourcesManager): CheckoutViewModel = CheckoutViewModel(repository, resourcesManager)

    @Provides
    fun providesViewUtils(): ViewUtils = ViewUtils()

}