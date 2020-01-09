package com.raul.androidapps.lanaapplication.persistence

import com.raul.androidapps.lanaapplication.persistence.databases.AppDatabase
import com.raul.androidapps.lanaapplication.persistence.entities.FooEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistenceManagerImpl @Inject constructor(
    private val db: AppDatabase
) : PersistenceManager {

    override suspend fun getFoo(name: String): List<FooEntity> =
        db.fooDao().getFoo()

}

