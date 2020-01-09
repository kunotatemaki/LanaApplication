package com.raul.androidapps.testapplication.persistence

import com.raul.androidapps.testapplication.persistence.entities.FooEntity

interface PersistenceManager {

    suspend fun getFoo(name: String): List<FooEntity>

}