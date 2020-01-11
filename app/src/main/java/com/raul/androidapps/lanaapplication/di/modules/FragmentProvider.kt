package com.raul.androidapps.lanaapplication.di.modules

import com.raul.androidapps.lanaapplication.di.interfaces.CustomScopes
import com.raul.androidapps.lanaapplication.ui.checkout.CheckoutDialogFragment
import com.raul.androidapps.lanaapplication.ui.main.ProductsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Suppress("unused")
@Module
abstract class FragmentsProvider {

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesMainFragmentFactory(): ProductsFragment

    @CustomScopes.FragmentScope
    @ContributesAndroidInjector
    abstract fun providesCheckoutFragmentFactory(): CheckoutDialogFragment

}