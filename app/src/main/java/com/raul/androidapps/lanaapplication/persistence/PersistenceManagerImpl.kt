package com.raul.androidapps.lanaapplication.persistence

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.raul.androidapps.lanaapplication.domain.Item
import com.raul.androidapps.lanaapplication.persistence.databases.AppDatabase
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistenceManagerImpl @Inject constructor(
    private val db: AppDatabase
) : PersistenceManager {

    override fun getProducts(): LiveData<PagedList<ProductEntity>>  =
        db.productDao().getProducts().toLiveData(pageSize = 30)

    override suspend fun storeProducts(products: List<Item>) {
        db.productDao().insert(products.map { ProductEntity.from(it) })
    }
}

