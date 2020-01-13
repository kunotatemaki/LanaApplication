package com.raul.androidapps.lanaapplication.repository

import androidx.lifecycle.LiveData
import com.raul.androidapps.lanaapplication.network.AppApi
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.vo.Result

interface Repository {

    fun getProducts(forceFetchInfo: Boolean): LiveData<Result<List<ProductEntity>>>
    fun getProductsFromCache(): LiveData<List<ProductEntity>>
    fun getProductsInBasket(): LiveData<List<BasketEntity>>
    suspend fun getProductsFromNetwork(): Result<AppApi.Products>
    suspend fun addProductToBasket(code: String)
    suspend fun removeProductFromBasket(code: String)
    suspend fun clearBasket()

}