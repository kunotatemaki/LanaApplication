package com.raul.androidapps.lanaapplication.ui.main

import androidx.lifecycle.*
import com.raul.androidapps.lanaapplication.domain.Product
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.vo.Result

open class MainViewModel constructor(private val repository: Repository) : ViewModel() {

    private var products: MediatorLiveData<Result<List<Product>>> = MediatorLiveData()
    private var productsFromDb: LiveData<Result<List<ProductEntity>>>
    private val fetchTrigger = MutableLiveData<Long>()

    init {
        productsFromDb = fetchTrigger.switchMap {
            if (it == null || it == 0L) {
                repository.getProducts()
            } else {
                repository.getProducts(true)
            }
        }
        products.addSource(productsFromDb) {
            //apply offers
            val list = it.data?.map { product->  Product.from(product)}
            val result = when(it.status){
                Result.Status.SUCCESS -> Result.success(list)
                Result.Status.ERROR -> Result.loading(list)
                Result.Status.LOADING -> Result.error(it.message, list)
            }
            products.value = result
        }
        fetchTrigger.value = 0
    }

    fun getProductsAsObservable(): LiveData<Result<List<Product>>> =
        Transformations.distinctUntilChanged(products)


    fun refresh(){
        fetchTrigger.value = System.currentTimeMillis()
    }
}
