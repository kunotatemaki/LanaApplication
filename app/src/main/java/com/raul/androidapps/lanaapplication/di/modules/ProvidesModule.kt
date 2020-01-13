package com.raul.androidapps.lanaapplication.di.modules

import android.content.Context
import com.raul.androidapps.lanaapplication.MyApplication
import com.raul.androidapps.lanaapplication.domain.usecase.AddProductToBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.ClearBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.GetProductsUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.RemoveProductFromBasketUseCase
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
    fun provideMainViewModel(
        getProductsUseCase: GetProductsUseCase,
        addProductToBasketUseCase: AddProductToBasketUseCase,
        removeProductFromBasketUseCase: RemoveProductFromBasketUseCase,
        clearBasketUseCase: ClearBasketUseCase
    ): ProductsViewModel = ProductsViewModel(
        getProductsUseCase,
        addProductToBasketUseCase,
        removeProductFromBasketUseCase,
        clearBasketUseCase
    )

    @Provides
    fun provideCheckoutViewModel(
        getProductsUseCase: GetProductsUseCase,
        clearBasketUseCase: ClearBasketUseCase,
        resourcesManager: ResourcesManager
    ): CheckoutViewModel = CheckoutViewModel(getProductsUseCase, clearBasketUseCase, resourcesManager)

    @Provides
    @Singleton
    fun providesViewUtils(): ViewUtils = ViewUtils()

    @Provides
    @Singleton
    fun providesGetProductsUseCase(repository: Repository): GetProductsUseCase = GetProductsUseCase(repository)

    @Provides
    @Singleton
    fun providesAddProductsToBasketUseCase(repository: Repository): AddProductToBasketUseCase =
        AddProductToBasketUseCase(repository)

    @Provides
    @Singleton
    fun providesRemoveProductsFromBasketUseCase(repository: Repository): RemoveProductFromBasketUseCase =
        RemoveProductFromBasketUseCase(repository)

    @Provides
    @Singleton
    fun providesClearBasketUseCase(repository: Repository): ClearBasketUseCase = ClearBasketUseCase(repository)

}