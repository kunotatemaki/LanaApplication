package com.raul.androidapps.lanaapplication.di.modules

import com.raul.androidapps.lanaapplication.network.NetworkServiceFactory
import com.raul.androidapps.lanaapplication.network.NetworkServiceFactoryImpl
import com.raul.androidapps.lanaapplication.persistence.PersistenceManager
import com.raul.androidapps.lanaapplication.persistence.PersistenceManagerImpl
import com.raul.androidapps.lanaapplication.preferences.PreferencesManager
import com.raul.androidapps.lanaapplication.preferences.PreferencesManagerImpl
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.repository.RepositoryImpl
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.resources.ResourcesManagerImpl
import com.raul.androidapps.lanaapplication.security.Encryption
import com.raul.androidapps.lanaapplication.security.EncryptionImpl
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