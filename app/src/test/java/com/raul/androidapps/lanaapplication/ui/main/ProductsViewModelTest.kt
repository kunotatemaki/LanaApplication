package com.raul.androidapps.lanaapplication.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.raul.androidapps.lanaapplication.domain.usecase.AddProductToBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.ClearBasketUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.GetProductsUseCase
import com.raul.androidapps.lanaapplication.domain.usecase.RemoveProductFromBasketUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @MockK
    private lateinit var getProductsUseCase: GetProductsUseCase

    @MockK
    private lateinit var addProductToBasketUseCase: AddProductToBasketUseCase

    @MockK
    private lateinit var removeProductFromBasketUseCase: RemoveProductFromBasketUseCase

    @MockK
    private lateinit var clearBasketUseCase: ClearBasketUseCase

    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ProductsViewModel(
            getProductsUseCase,
            addProductToBasketUseCase,
            removeProductFromBasketUseCase,
            clearBasketUseCase
        )
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
            coVerify(exactly = 1) { addProductToBasketUseCase.addProductToBasket("code") }

        }
    }

    @Test
    fun removeProductToBasket() {
        runBlocking(Dispatchers.IO) {
            viewModel.removeProductToBasket("code").join()
            coVerify(exactly = 1) { removeProductFromBasketUseCase.removeProductFromBasket("code") }

        }
    }

    @Test
    fun clearBasket() {
        runBlocking(Dispatchers.IO) {
            viewModel.clearBasket().join()
            coVerify(exactly = 1) { clearBasketUseCase.clearBasket() }

        }

    }
}