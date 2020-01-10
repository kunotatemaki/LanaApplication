package com.raul.androidapps.lanaapplication.persistence.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raul.androidapps.lanaapplication.persistence.daos.BasketDao
import com.raul.androidapps.lanaapplication.persistence.daos.ProductDao
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.persistence.utils.DbConverters
import com.raul.androidapps.lanaapplication.persistence.utils.PersistenceConstants

@Database(entities = [(ProductEntity::class), (BasketEntity::class)], exportSchema = false, version = 1)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun basketDao(): BasketDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: buildDatabase(
                context
            ).also { INSTANCE = it }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, PersistenceConstants.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}
