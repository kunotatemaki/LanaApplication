package com.raul.androidapps.lanaapplication.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.raul.androidapps.lanaapplication.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    private lateinit var repository: Repository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun refresh() {
        runBlocking(Dispatchers.IO) {
            val triggerObserver = viewModel.fetchTrigger.test()
            viewModel.refresh()

            viewModel
            triggerObserver
                .assertHasValue()
                .assertHistorySize(2)

        }
    }

    @Test
    fun addProductToBasket() {
        runBlocking(Dispatchers.IO) {
            viewModel.addProductToBasket("code").join()
            verify(repository, times(1)).addProductToBasket("code")

        }
    }

    @Test
    fun removeProductToBasket() {
        runBlocking(Dispatchers.IO) {
            viewModel.removeProductToBasket("code").join()
            verify(repository, times(1)).removeProductFromBasket("code")

        }
    }

    @Test
    fun clearBasket() {
        runBlocking(Dispatchers.IO) {
            viewModel.clearBasket().join()
            verify(repository, times(1)).clearBasket()

        }

    }
}