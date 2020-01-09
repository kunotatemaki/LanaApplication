package testclasses

import com.raul.androidapps.lanaapplication.MyApplication
import com.raul.androidapps.lanaapplication.di.modules.ActivityBuilder
import com.raul.androidapps.lanaapplication.di.modules.FragmentsProvider
import com.raul.androidapps.lanaapplication.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class), (ActivityBuilder::class), (ViewModelModule::class),
        (AppModuleForTest::class), (FragmentsProvider::class)]
)
interface AppComponentForTest : AndroidInjector<MyApplication> {

    override fun inject(currencyConverterApp: MyApplication)

    @Component.Builder
    interface Builder {
        fun appModuleForTest(appModuleForTest: AppModuleForTest): Builder

        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): AppComponentForTest
    }

}