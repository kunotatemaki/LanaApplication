package testclasses

import android.content.Context
import com.raul.androidapps.testapplication.MyApplication
import com.raul.androidapps.testapplication.network.NetworkServiceFactory
import com.raul.androidapps.testapplication.network.NetworkServiceFactoryImpl
import com.raul.androidapps.testapplication.preferences.PreferencesManager
import com.raul.androidapps.testapplication.preferences.PreferencesManagerImpl
import com.raul.androidapps.testapplication.repository.Repository
import com.raul.androidapps.testapplication.repository.RepositoryImpl
import com.raul.androidapps.testapplication.resources.ResourcesManager
import com.raul.androidapps.testapplication.resources.ResourcesManagerImpl
import com.raul.androidapps.testapplication.security.Encryption
import com.raul.androidapps.testapplication.security.EncryptionImpl
import com.raul.androidapps.testapplication.ui.main.MainViewModel
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
    open fun provideRepository(repositoryImpl: RepositoryImpl): Repository = repositoryImpl


}