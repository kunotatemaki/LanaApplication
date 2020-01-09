package com.raul.androidapps.lanaapplication.ui.main

import androidx.lifecycle.*
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.vo.Result

open class MainViewModel constructor(private val repository: Repository) : ViewModel() {

    private var products: LiveData<Result<List<ProductEntity>>>
    private val fetchTrigger = MutableLiveData<Long>()

    init {
        products = fetchTrigger.switchMap {
            if (it == null || it == 0L) {
                repository.getProducts()
            } else {
                repository.getProducts(true)
            }
        }
        fetchTrigger.value = 0
    }

    fun getProductsAsObservable(): LiveData<Result<List<ProductEntity>>> =
        Transformations.distinctUntilChanged(products)


    fun test(){
        fetchTrigger.value = System.currentTimeMillis()
    }
}
