package com.raul.androidapps.lanaapplication.persistence

import androidx.lifecycle.LiveData
import com.raul.androidapps.lanaapplication.domain.model.mapper.ApiToEntityMapper
import com.raul.androidapps.lanaapplication.network.AppApi.Item
import com.raul.androidapps.lanaapplication.persistence.databases.AppDatabase
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistenceManagerImpl @Inject constructor(
    private val db: AppDatabase
) : PersistenceManager {

    override fun getProducts(): LiveData<List<ProductEntity>> =
        db.productDao().getProducts()

    override suspend fun storeProducts(products: List<Item>) {
        db.productDao().insert(products.map { ApiToEntityMapper.map(it) })
    }

    override fun getProductsInBasket(): LiveData<List<BasketEntity>> =
        db.basketDao().getProductsInBasket()

    override suspend fun getProductFromBasket(code: String): BasketEntity? =
        db.basketDao().getProductFromBasket(code)

    override suspend fun addProductToBasket(code: String) {
        db.basketDao().insertAndDeleteInTransaction(code, 1)
    }

    override suspend fun removeProductFromBasket(code: String) {
        db.basketDao().insertAndDeleteInTransaction(code, -1)
    }

    override suspend fun clearBasket() {
        db.basketDao().delete()
    }
}

