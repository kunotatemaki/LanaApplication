package com.raul.androidapps.testapplication.di.modules

import com.raul.androidapps.testapplication.network.NetworkServiceFactory
import com.raul.androidapps.testapplication.network.NetworkServiceFactoryImpl
import com.raul.androidapps.testapplication.persistence.PersistenceManager
import com.raul.androidapps.testapplication.persistence.PersistenceManagerImpl
import com.raul.androidapps.testapplication.preferences.PreferencesManager
import com.raul.androidapps.testapplication.preferences.PreferencesManagerImpl
import com.raul.androidapps.testapplication.repository.Repository
import com.raul.androidapps.testapplication.repository.RepositoryImpl
import com.raul.androidapps.testapplication.resources.ResourcesManager
import com.raul.androidapps.testapplication.resources.ResourcesManagerImpl
import com.raul.androidapps.testapplication.security.Encryption
import com.raul.androidapps.testapplication.security.EncryptionImpl
import dagger.Binds
import dagger.Module


@Module(includes = [(ViewModelModule::class)])
abstract class BindsModule {


    @Binds
    abstract fun provideResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager

    @Binds
    abstract fun providePersistenceManager(persistenceManagerImpl: PersistenceManagerImpl): PersistenceManager

    @Binds
    abstract fun provideNetworkServiceFactory(networkServiceFactoryImp: NetworkServiceFactoryImpl): NetworkServiceFactory

    @Binds
    abstract fun provideEncryption(encryptionImpl: EncryptionImpl): Encryption

    @Binds
    abstract fun providePreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager

    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}