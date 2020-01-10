package com.raul.androidapps.lanaapplication.persistence.daos

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity


@Dao
abstract class ProductDao : BaseDao<ProductEntity>() {

    @Query("SELECT * FROM product")
    abstract fun getProducts(): LiveData<List<ProductEntity>>

}