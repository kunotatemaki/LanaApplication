package com.raul.androidapps.lanaapplication.domain.usecase

import androidx.lifecycle.LiveData
import com.raul.androidapps.lanaapplication.network.AppApi
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.vo.Result


class GetProductsUseCase constructor(private val repository: Repository) {

    fun getProducts(forceFetchInfo: Boolean = false): LiveData<Result<List<ProductEntity>>> =
        repository.getProducts(forceFetchInfo)

    fun getProductsInBasket(): LiveData<List<BasketEntity>> =
        repository.getProductsInBasket()

    fun getProductsFromCache(): LiveData<List<ProductEntity>> =
        repository.getProductsFromCache()

    suspend fun getProductsFromNetwork(): Result<AppApi.Products> =
        repository.getProductsFromNetwork()


}