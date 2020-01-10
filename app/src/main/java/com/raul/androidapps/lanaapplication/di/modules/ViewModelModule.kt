package com.raul.androidapps.lanaapplication.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raul.androidapps.lanaapplication.di.interfaces.ViewModelKey
import com.raul.androidapps.lanaapplication.ui.checkout.CheckoutViewModel
import com.raul.androidapps.lanaapplication.ui.common.ViewModelFactory
import com.raul.androidapps.lanaapplication.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap



@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckoutViewModel::class)
    internal abstract fun bindCheckoutViewModel(checkoutViewModel: CheckoutViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}