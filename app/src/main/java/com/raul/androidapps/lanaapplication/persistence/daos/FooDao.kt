package com.raul.androidapps.lanaapplication.persistence.daos

import androidx.room.Dao
import androidx.room.Query
import com.raul.androidapps.lanaapplication.persistence.entities.FooEntity


@Dao
abstract class FooDao : BaseDao<FooEntity>() {

    @Query("SELECT * FROM getFlights")
    abstract suspend fun getFoo(): List<FooEntity>


}