package com.raul.androidapps.lanaapplication.persistence

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.raul.androidapps.lanaapplication.domain.Item
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity

interface PersistenceManager {

    fun getProducts(): LiveData<PagedList<ProductEntity>>
    suspend fun storeProducts(products: List<Item>)

}