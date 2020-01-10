package com.raul.androidapps.lanaapplication.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.lanaapplication.network.AppApi.Item
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity

interface PersistenceManager {

    fun getProducts(): LiveData<List<ProductEntity>>
    suspend fun storeProducts(products: List<Item>)
    fun getProductsInBasket(): LiveData<List<BasketEntity>>
    suspend fun getProductFromBasket(code: String): BasketEntity?
    suspend fun addProductToBasket(code: String)
    suspend fun removeProductFromBasket(code: String)
    suspend fun clearBasket()

}