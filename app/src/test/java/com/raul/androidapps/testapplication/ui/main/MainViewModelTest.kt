package com.raul.androidapps.testapplication.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.testapplication.domain.NetworkResponse
import com.raul.androidapps.testapplication.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class MainViewModelTest {

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @InjectMocks
    private lateinit var viewModel: MainViewModel

    private val successResponse = 9

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getFooSuccess() {
        runBlocking {
            whenever(repository.getFoo())
                .thenReturn(NetworkResponse.Success(successResponse))

            val testObserver = viewModel.needToShowLoading().test()

            val job = viewModel.getFoo()
            job.join()

            testObserver.assertHistorySize(2)
                .assertValueHistory(true, false)

            assertEquals(successResponse, viewModel.getFooAsObservable().value)
            assertNull(viewModel.needToShowError().value)
        }
    }

    @Test
    fun getFooError() {
        val error = "some error"
        runBlocking {
            whenever(repository.getFoo())
                .thenReturn(NetworkResponse.Failure(error))

            val testObserver = viewModel.needToShowLoading().test()

            val job = viewModel.getFoo()
            job.join()

            testObserver.assertHistorySize(2)
                .assertValueHistory(true, false)

            assertEquals(error, viewModel.needToShowError().value)
        }

    }
}
