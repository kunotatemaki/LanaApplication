package com.raul.androidapps.testapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.testapplication.domain.NetworkResponse
import com.raul.androidapps.testapplication.repository.Repository
import com.raul.androidapps.testapplication.ui.MainActivity
import com.raul.androidapps.testapplication.ui.main.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setUp() {

    }

    @Test
    fun useAppContext() {
        runBlocking {
            whenever(repository.getFoo())
                .thenReturn(NetworkResponse.Success(Any()))
            //both repo and viewmodel mocks will be injected by dagger

            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            assertEquals("com.raul.androidapps.testapplication", appContext.packageName)
        }

    }
}
