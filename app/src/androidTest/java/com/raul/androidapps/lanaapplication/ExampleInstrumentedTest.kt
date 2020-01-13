package com.raul.androidapps.lanaapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.ui.MainActivity
import com.raul.androidapps.lanaapplication.ui.main.ProductsViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Rule
    @JvmField
    var rule = espressoDaggerMockRule()

    @Mock
    private lateinit var viewModel: ProductsViewModel

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setUp() {

    }

    @Test
    fun useAppContext() {
        runBlocking {

            //both repo and viewmodel mocks will be injected by dagger

            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            assertEquals("com.raul.androidapps.lanaapplication", appContext.packageName)
        }

    }
}
