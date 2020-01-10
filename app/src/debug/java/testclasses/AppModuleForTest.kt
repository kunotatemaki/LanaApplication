package testclasses

import android.content.Context
import com.raul.androidapps.lanaapplication.MyApplication
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
import com.raul.androidapps.lanaapplication.ui.main.MainViewModel
import com.raul.androidapps.lanaapplication.utils.RateLimiter
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
    open fun providesMainViewModel(repository: Repository): MainViewModel =
        MainViewModel(repository)

    @Provides
    open fun providesCheckoutViewModel(repository: Repository): CheckoutViewModel =
        CheckoutViewModel(repository)

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
}