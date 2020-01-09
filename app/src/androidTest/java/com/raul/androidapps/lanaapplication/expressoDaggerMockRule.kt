package com.raul.androidapps.lanaapplication

import androidx.test.platform.app.InstrumentationRegistry
import it.cosenonjaviste.daggermock.DaggerMock
import testclasses.AppComponentForTest
import testclasses.AppModuleForTest


fun espressoDaggerMockRule() = DaggerMock.rule<AppComponentForTest>(AppModuleForTest()) {
    set { component -> component.inject(app) }
    customizeBuilder<AppComponentForTest.Builder> { it.application(app) }
}

val app: MyApplication
    get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApplication