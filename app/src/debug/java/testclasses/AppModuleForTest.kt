package testclasses

import android.content.Context
import com.raul.androidapps.lanaapplication.MyApplication
import com.raul.androidapps.lanaapplication.domain.usecase.AddProductToBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.ClearBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.GetProductsUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.RemoveProductFromBasketUseCase
import com.raul.androidapps.lanaapplication.network.NetworkServiceFactory
import com.raul.androidapps.lanaapplication.network.NetworkServiceFactoryImpl
import com.raul.androidapps.lanaapplication.persistence.PersistenceManager
import com.raul.androidapps.lanaapplication.persistence.PersistenceManagerImpl
import com.raul.androidapps.lanaapplication.persistence.databases.AppDatabase
import com.raul.androidapps.lanaapplication.preferences.PreferencesManager
import com.raul.androidapps.lanaapplication.preferences.PreferencesManagerImpl
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.repository.RepositoryImpl
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.resources.ResourcesManagerImpl
import com.raul.androidapps.lanaapplication.security.Encryption
import com.raul.androidapps.lanaapplication.security.EncryptionImpl
import com.raul.androidapps.lanaapplication.ui.checkout.CheckoutViewModel
import com.raul.androidapps.lanaapplication.ui.main.ProductsViewModel
import com.raul.androidapps.lanaapplication.utils.RateLimiter
import com.raul.androidapps.lanaapplication.utils.ViewUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Suppress("unused")
@Module
open class AppModuleForTest {

    @Singleton
    @Provides
    open fun providesContext(application: MyApplication): Context =
        application.applicationContext

    @Provides
    open fun providesMainViewModel(
        getProductsUseCase: GetProductsUseCase,
        addProductToBasketUseCase: AddProductToBasketUseCase,
        removeProductFromBasketUseCase: RemoveProductFromBasketUseCase,
        clearBasketUseCase: ClearBasketUseCase
    ): ProductsViewModel =
        ProductsViewModel(
            getProductsUseCase,
            addProductToBasketUseCase,
            removeProductFromBasketUseCase,
            clearBasketUseCase
        )

    @Provides
    open fun providesCheckoutViewModel(
        getProductsUseCase: GetProductsUseCase,
        clearBasketUseCase: ClearBasketUseCase,
        resourcesManager: ResourcesManager
    ): CheckoutViewModel =
        CheckoutViewModel(getProductsUseCase, clearBasketUseCase, resourcesManager)

    @Singleton
    @Provides
    open fun provideResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager =
        resourcesManagerImpl

    @Singleton
    @Provides
    open fun provideNetworkServiceFactory(networkServiceFactoryImp: NetworkServiceFactoryImpl): NetworkServiceFactory =
        networkServiceFactoryImp

    @Singleton
    @Provides
    open fun provideEncryption(encryptionImpl: EncryptionImpl): Encryption = encryptionImpl

    @Singleton
    @Provides
    open fun providePreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager =
        preferencesManagerImpl

    @Singleton
    @Provides
    open fun providePersistenceManager(persistenceManagerImpl: PersistenceManagerImpl): PersistenceManager =
        persistenceManagerImpl

    @Singleton
    @Provides
    open fun provideRateLimiter(): RateLimiter = RateLimiter()

    @Singleton
    @Provides
    open fun provideRepository(repositoryImpl: RepositoryImpl): Repository = repositoryImpl

    @Singleton
    @Provides
    fun provideDb(
        context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun providesViewUtils(): ViewUtils = ViewUtils()

    @Provides
    @Singleton
    fun providesGetProductsUseCase(repository: Repository): GetProductsUseCase =
        GetProductsUseCase(repository)

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
    fun providesClearBasketUseCase(repository: Repository): ClearBasketUseCase =
        ClearBasketUseCase(repository)
}