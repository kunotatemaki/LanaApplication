package com.raul.androidapps.lanaapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.lanaapplication.domain.Products
import com.raul.androidapps.lanaapplication.network.AppApi
import com.raul.androidapps.lanaapplication.network.NetworkServiceFactory
import com.raul.androidapps.lanaapplication.persistence.PersistenceManager
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.preferences.PreferencesManager
import com.raul.androidapps.lanaapplication.repository.RepositoryImpl
import com.raul.androidapps.lanaapplication.utils.RateLimiter
import com.raul.androidapps.lanaapplication.vo.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@ObsoleteCoroutinesApi
@Suppress("BlockingMethodInNonBlockingContext")
@ExperimentalCoroutinesApi
class RepositoryTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkServiceFactory: NetworkServiceFactory

    @Mock
    lateinit var persistenceManager: PersistenceManager

    @Mock
    lateinit var preferencesManager: PreferencesManager

    @Mock
    lateinit var rateLimiter: RateLimiter

    @Mock
    lateinit var api: AppApi

    @InjectMocks
    private lateinit var repository: RepositoryImpl

    private lateinit var dbResponse: List<ProductEntity>
    private lateinit var networkResponse: Products
    private val dbProduct1 = ProductEntity("code1", "name1", 1.0)
    private val dbProduct2 = ProductEntity("code2", "name2", 2.0)
    private val dbProduct3 = ProductEntity("code3", "name3", 3.0)
    private val networkProduct1 = dbProduct1.toItem()
    private val networkProduct2 = dbProduct2.toItem()
    private val networkProduct3 = dbProduct3.toItem()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dbResponse = mutableListOf(dbProduct1, dbProduct2, dbProduct3)
        networkResponse = Products(listOf(networkProduct1, networkProduct2, networkProduct3))
        Dispatchers.setMain(mainThreadSurrogate)
        whenever(networkServiceFactory.getServiceInstance())
            .thenReturn(
                api
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Test
    fun `load data from db and make a network call`() {
        runBlocking(Dispatchers.IO) {
            whenever(persistenceManager.getProducts())
                .thenReturn(
                    MutableLiveData<List<ProductEntity>>().also {
                        it.value = dbResponse
                    }
                )
            whenever(api.getProducts())
                .thenReturn(
                    Response.success(200, networkResponse)
                )
            whenever(rateLimiter.shouldFetch(anyLong(), anyInt(), any(TimeUnit::class.java)))
                .thenReturn(
                    true
                )
            val response = repository.getProducts()
            val responseObserver = response.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<List<ProductEntity>>> {
                latch.countDown()
            }
            response.observeForever(observer)
            latch.await(1000, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.success(dbResponse))
                .assertValueHistory(Result.loading(dbResponse), Result.success(dbResponse))
        }
    }

    @Test
    fun `no data from db and no network call`() {
        runBlocking(Dispatchers.IO) {
            whenever(persistenceManager.getProducts())
                .thenReturn(
                    MutableLiveData<List<ProductEntity>>().also {
                        it.value = listOf()
                    }
                )
            whenever(api.getProducts())
                .thenReturn(
                    Response.success(200, networkResponse)
                )
            whenever(rateLimiter.shouldFetch(anyLong(), anyInt(), any(TimeUnit::class.java)))
                .thenReturn(
                    false
                )
            val response = repository.getProducts()
            val responseObserver = response.test()
            val latch = CountDownLatch(1)
            val observer = Observer<Result<List<ProductEntity>>> {
                latch.countDown()
            }
            response.observeForever(observer)
            latch.await(1000, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(1)
                .assertValue(Result.success(listOf()))
        }
    }

    @Test
    fun `load data from db and no network call`() {
        runBlocking(Dispatchers.IO) {
            whenever(persistenceManager.getProducts())
                .thenReturn(
                    MutableLiveData<List<ProductEntity>>().also {
                        it.value = dbResponse
                    }
                )
            whenever(api.getProducts())
                .thenReturn(
                    Response.success(200, networkResponse)
                )
            whenever(rateLimiter.shouldFetch(anyLong(), anyInt(), any(TimeUnit::class.java)))
                .thenReturn(
                    false
                )
            val response = repository.getProducts()
            val responseObserver = response.test()
            val latch = CountDownLatch(1)
            val observer = Observer<Result<List<ProductEntity>>> {
                latch.countDown()
            }
            response.observeForever(observer)
            latch.await(1000, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(1)
                .assertValue(Result.success(dbResponse))
        }
    }

    @Test
    fun `load data from db and error in the network call`() {
        runBlocking(Dispatchers.IO) {
            whenever(persistenceManager.getProducts())
                .thenReturn(
                    MutableLiveData<List<ProductEntity>>().also {
                        it.value = dbResponse
                    }
                )
            whenever(api.getProducts())
                .thenReturn(
                    Response.error(
                        500,
                        "{\"message\":\"some_value\"}".toResponseBody(
                            "application/json".toMediaType()
                        )
                    )
                )
            whenever(rateLimiter.shouldFetch(anyLong(), anyInt(), any(TimeUnit::class.java)))
                .thenReturn(
                    true
                )
            val response = repository.getProducts()
            val responseObserver = response.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<List<ProductEntity>>> {
                latch.countDown()
            }
            response.observeForever(observer)
            latch.await(1000, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.error("Response.error()", dbResponse))
                .assertValueHistory(
                    Result.loading(dbResponse),
                    Result.error("Response.error()", dbResponse)
                )
        }
    }

    @Test
    fun `load data from db and exception in the network call`() {
        runBlocking(Dispatchers.IO) {
            whenever(persistenceManager.getProducts())
                .thenReturn(
                    MutableLiveData<List<ProductEntity>>().also {
                        it.value = dbResponse
                    }
                )
            whenever(api.getProducts())
                .then {
                    @Suppress("DIVISION_BY_ZERO")
                    1 / 0
                }
            whenever(rateLimiter.shouldFetch(anyLong(), anyInt(), any(TimeUnit::class.java)))
                .thenReturn(
                    true
                )
            val response = repository.getProducts()
            val responseObserver = response.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<List<ProductEntity>>> {
                latch.countDown()
            }
            response.observeForever(observer)
            latch.await(1000, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.error("error fetching from network", dbResponse))
                .assertValueHistory(
                    Result.loading(dbResponse),
                    Result.error("error fetching from network", dbResponse)
                )
        }
    }

    @Test
    fun `force to make a network call even when shouldFetch returns false`() {
        runBlocking(Dispatchers.IO) {
            whenever(persistenceManager.getProducts())
                .thenReturn(
                    MutableLiveData<List<ProductEntity>>().also {
                        it.value = dbResponse
                    }
                )
            whenever(api.getProducts())
                .thenReturn(
                    Response.success(200, networkResponse)
                )
            whenever(rateLimiter.shouldFetch(anyLong(), anyInt(), any(TimeUnit::class.java)))
                .thenReturn(
                    false
                )
            val response = repository.getProducts(forceFetchInfo = true)
            val responseObserver = response.test()
            val latch = CountDownLatch(2)
            val observer = Observer<Result<List<ProductEntity>>> {
                latch.countDown()
            }
            response.observeForever(observer)
            latch.await(1000, TimeUnit.SECONDS)
            responseObserver
                .assertHasValue()
                .assertHistorySize(2)
                .assertValue(Result.success(dbResponse))
                .assertValueHistory(
                    Result.loading(dbResponse),
                    Result.success(dbResponse)
                )
        }
    }

}
