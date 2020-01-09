package com.raul.androidapps.lanaapplication.persistence

import com.raul.androidapps.lanaapplication.persistence.entities.FooEntity

interface PersistenceManager {

    suspend fun getFoo(name: String): List<FooEntity>

}