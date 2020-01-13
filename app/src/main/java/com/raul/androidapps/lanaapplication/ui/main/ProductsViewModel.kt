package com.raul.androidapps.lanaapplication.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.raul.androidapps.lanaapplication.domain.model.Product
import com.raul.androidapps.lanaapplication.domain.model.mapper.EntityToProduct
import com.raul.androidapps.lanaapplication.domain.usecase.AddProductToBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.ClearBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.GetProductsUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.RemoveProductFromBasketUseCase
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.vo.Result
import kotlinx.coroutines.launch

open class ProductsViewModel constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToBasketUseCase: AddProductToBasketUseCase,
    private val removeProductFromBasketUseCase: RemoveProductFromBasketUseCase,
    private val clearBasketUseCase: ClearBasketUseCase
) : ViewModel() {

    private var products: MediatorLiveData<Result<List<Product>>> = MediatorLiveData()
    private var productsFromDb: LiveData<Result<List<ProductEntity>>>
    private var productsIbBasket: LiveData<List<BasketEntity>> = getProductsUseCase.getProductsInBasket()

    @VisibleForTesting
    val fetchTrigger = MutableLiveData<Long>()

    init {
        productsFromDb = fetchTrigger.switchMap {
            if (it == null || it == 0L) {
                getProductsUseCase.getProducts()
            } else {
                getProductsUseCase.getProducts(true)
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
        val list = productsFromDb.value?.data?.map { product -> EntityToProduct.map(product) }
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

    fun addProductToBasket(code: String) =
        viewModelScope.launch {
            addProductToBasketUseCase.addProductToBasket(code)
        }

    fun removeProductToBasket(code: String) =
        viewModelScope.launch {
            removeProductFromBasketUseCase.removeProductFromBasket(code)
        }

    fun clearBasket() =
        viewModelScope.launch {
            clearBasketUseCase.clearBasket()
        }

    fun getNumberOfSelectedItems(): Int =
        productsIbBasket.value?.sumBy { it.selections } ?: 0

}
