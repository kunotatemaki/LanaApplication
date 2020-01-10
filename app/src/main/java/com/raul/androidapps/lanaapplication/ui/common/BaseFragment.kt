package com.raul.androidapps.lanaapplication.ui.common

import com.raul.androidapps.lanaapplication.databinding.BindingComponent
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    @Inject
    protected lateinit var bindingComponent: BindingComponent

    @Inject
    protected lateinit var resourcesManager: ResourcesManager

}