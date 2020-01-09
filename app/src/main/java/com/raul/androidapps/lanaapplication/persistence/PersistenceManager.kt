package com.raul.androidapps.lanaapplication.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.lanaapplication.domain.Item
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity

interface PersistenceManager {

    fun getProducts(): LiveData<List<ProductEntity>>
    suspend fun storeProducts(products: List<Item>)

}