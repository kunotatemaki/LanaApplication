package com.raul.androidapps.lanaapplication


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.raul.androidapps.lanaapplication.persistence.PersistenceManager
import com.raul.androidapps.lanaapplication.persistence.PersistenceManagerImpl
import com.raul.androidapps.lanaapplication.persistence.databases.AppDatabase
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("BlockingMethodInNonBlockingContext")
@RunWith(AndroidJUnit4::class)
class LocalDBTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var persistenceManager: PersistenceManager

    private val dbProduct1 = ProductEntity("code1", "name1", 1.0)
    private val dbProduct2 = ProductEntity("code2", "name2", 2.0)
    private val dbProduct3 = ProductEntity("code3", "name3", 3.0)
    private val networkProduct1 = dbProduct1.toItem()
    private val networkProduct2 = dbProduct2.toItem()
    private val networkProduct3 = dbProduct3.toItem()

    @Before
    @Throws(Exception::class)
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build() // allowing main thread queries, just for testing

        persistenceManager = PersistenceManagerImpl(database)

    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }


    @Test
    @Throws(InterruptedException::class)
    fun testProductsTableEmpty() {
        runBlocking {
            val products = persistenceManager.getProducts()
            assertTrue(products.getItem().isEmpty())
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun testOneProduct() {
        runBlocking {
            persistenceManager.storeProducts(listOf(networkProduct1))
            val products = persistenceManager.getProducts()

            assertTrue(products.getItem().size == 1)
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun testMoreThanOneProduct() {
        runBlocking {
            persistenceManager.storeProducts(listOf(networkProduct1, networkProduct2, networkProduct3))
            val products = persistenceManager.getProducts()

            assertTrue(products.getItem().size == 3)
        }
    }


}