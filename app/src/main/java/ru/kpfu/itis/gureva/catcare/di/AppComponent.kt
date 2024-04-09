package ru.kpfu.itis.gureva.catcare.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.kpfu.itis.gureva.catcare.data.di.DataBinderModule
import ru.kpfu.itis.gureva.catcare.data.di.NetworkModule
import ru.kpfu.itis.gureva.catcare.data.di.DataModule
import ru.kpfu.itis.gureva.catcare.presentation.MainActivity
import ru.kpfu.itis.gureva.catcare.presentation.di.ViewModelBinderModule
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.CatFactFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.registration.RegistrationFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DataBinderModule::class,
    ViewModelBinderModule::class,
    DataModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HelpfulFragment)
    fun inject(fragment: CatFactFragment)
    fun inject(fragment: RegistrationFragment)
}

