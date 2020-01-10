package com.raul.androidapps.lanaapplication.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity


@Dao
abstract class BasketDao : BaseDao<BasketEntity>() {

    @Query("SELECT * FROM basket")
    abstract fun getProductsInBasket(): LiveData<List<BasketEntity>>

    @Query("SELECT * FROM basket WHERE code LIKE :code")
    abstract fun getProductFromBasket(code: String): BasketEntity?

    @Transaction
    open suspend fun insertAndDeleteInTransaction(
        code: String,
        add: Int
    ) { // Anything inside this method runs in a single transaction.
        val product = getProductFromBasket(code)
        if(product == null){
            if(add < 0) return
            insert(BasketEntity(code, 1))
        } else{
            update(BasketEntity(code, product.selections + add))
        }
    }

    @Query("DELETE FROM basket")
    abstract suspend fun delete()
}