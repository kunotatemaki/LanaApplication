package com.raul.androidapps.testapplication.persistence.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raul.androidapps.testapplication.persistence.daos.FooDao
import com.raul.androidapps.testapplication.persistence.entities.FooEntity
import com.raul.androidapps.testapplication.persistence.utils.DbConverters
import com.raul.androidapps.testapplication.persistence.utils.PersistenceConstants

@Database(entities = [(FooEntity::class)], exportSchema = false, version = 1)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fooDao(): FooDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context/*,
            preferenceManager: PreferencesManager,
            databasePopulateTool: DatabasePopulateTool*/
        ): AppDatabase =
            INSTANCE ?: buildDatabase(
                context/*,
                preferenceManager,
                databasePopulateTool*/
            ).also { INSTANCE = it }

        private fun buildDatabase(
            context: Context/*,
            preferenceManager: PreferencesManager,
            databasePopulateTool: DatabasePopulateTool*/
        ) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, PersistenceConstants.DATABASE_NAME
            )
                //.addMigrations()    //no migrations, version 1
                .fallbackToDestructiveMigration()
                // prepopulate the database after onCreate was called
                //uncomment if  need to populate
//                .addCallback(object : RoomDatabase.Callback() {
//                    override fun onOpen(db: SupportSQLiteDatabase) {
//                        super.onOpen(db)
//                        if (!preferenceManager.getBooleanFromPreferences(PreferencesConstants.PROPERTY_DB_POPULATED)) {
//                            //load fist the small file to get main cities ready earlier (for old devices)
//                            GlobalScope.launch(Dispatchers.IO) {
//                                databasePopulateTool.populateDb(
//                                    getInstance(
//                                        context,
//                                        preferenceManager,
//                                        databasePopulateTool
//                                    ).fooDao()
//                                )
//                            }
//                        }
//                    }
//                })
                .build()

    }
}
