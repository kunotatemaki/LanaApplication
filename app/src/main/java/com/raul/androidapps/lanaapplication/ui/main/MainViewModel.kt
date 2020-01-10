package com.raul.androidapps.lanaapplication.ui.main

import androidx.lifecycle.*
import com.raul.androidapps.lanaapplication.domain.Product
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.vo.Result

open class MainViewModel constructor(private val repository: Repository) : ViewModel() {

    private var products: MediatorLiveData<Result<List<Product>>> = MediatorLiveData()
    private var productsFromDb: LiveData<Result<List<ProductEntity>>>
    private var productsIbBasket: LiveData<List<BasketEntity>> = repository.getProductsInBasket()
    private val fetchTrigger = MutableLiveData<Long>()

    init {
        productsFromDb = fetchTrigger.switchMap {
            if (it == null || it == 0L) {
                repository.getProducts()
            } else {
                repository.getProducts(true)
            }
        }
        products.apply {
            addSource(productsFromDb) {
                getProductsWithBasketInfo()
            }
            addSource(productsIbBasket) {
                getProductsWithBasketInfo()
            }
        }
        fetchTrigger.value = 0
    }

    private fun getProductsWithBasketInfo() {
        val list = productsFromDb.value?.data?.map { product -> Product.from(product) }
        //add info about how many times the item has been added to the basket
        list?.forEach { product ->
            product.timesInBasket =
                productsIbBasket.value?.firstOrNull { itemInBasket -> itemInBasket.code == product.code }?.selections
                    ?: 0
        }

        val result = when (productsFromDb.value?.status) {
            Result.Status.SUCCESS -> Result.success(list)
            Result.Status.LOADING -> Result.loading(list)
            Result.Status.ERROR -> Result.error(productsFromDb.value?.message, list)
            else -> null
        }
        products.value = result
    }

    fun getProductsAsObservable(): LiveData<Result<List<Product>>> =
        Transformations.distinctUntilChanged(products)


    fun refresh() {
        fetchTrigger.value = System.currentTimeMillis()
    }
}
