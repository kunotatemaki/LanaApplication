package com.raul.androidapps.lanaapplication.databinding

import androidx.databinding.DataBindingComponent
import javax.inject.Inject

class BindingComponent @Inject constructor(private val bindingAdapters: BindingAdapters) :
    DataBindingComponent {
    override fun getBindingAdapters(): BindingAdapters {
        return bindingAdapters
    }
}