package com.raul.androidapps.lanaapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.lanaapplication.domain.NetworkResponse
import com.raul.androidapps.lanaapplication.network.AppApi
import com.raul.androidapps.lanaapplication.network.NetworkServiceFactory
import com.raul.androidapps.lanaapplication.repository.RepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response


class RepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkServiceFactory: NetworkServiceFactory

    @Mock
    lateinit var api: AppApi

    @InjectMocks
    private lateinit var repository: RepositoryImpl

    private lateinit var list: List<Any>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        list = mutableListOf(true, false, false)

        whenever(networkServiceFactory.getServiceInstance())
            .thenReturn(
                api
            )
    }

//    @Test
//    fun testResponseSuccess() {
//        runBlocking {
//            whenever(api.getFoo(anyString(), anyLong(), anyString()))
//                .thenReturn(
//                    Response.success(200, list)
//                )
//            val response = repository.getProducts()
//            assertTrue(response is NetworkResponse.Success)
//            assertEquals((response as NetworkResponse.Success).data, list)
//        }
//    }
//
//    @Test
//    fun testResponseError() {
//        runBlocking {
//
//            whenever(api.getFoo(anyString(), anyLong(), anyString()))
//                .thenReturn(
//                    Response.error(
//                        500,
//                        "{\"message\":\"some_value\"}".toResponseBody(
//                            "application/json".toMediaType()
//                        )
//                    )
//                )
//            val response = repository.getProducts()
//            assertTrue(response is NetworkResponse.Failure)
//            assertEquals((response as NetworkResponse.Failure).message, "Response.error()")
//        }
//    }
//
//    @Suppress("DIVISION_BY_ZERO")
//    @Test
//    fun testResponseException() {
//        runBlocking {
//            whenever(api.getFoo(anyString(), anyLong(), anyString()))
//                .then {
//                    1 / 0
//                }
//            val response = repository.getProducts()
//            assertTrue(response is NetworkResponse.Failure)
//            assertEquals((response as NetworkResponse.Failure).message, "error fetching from network")
//        }
//    }
}
