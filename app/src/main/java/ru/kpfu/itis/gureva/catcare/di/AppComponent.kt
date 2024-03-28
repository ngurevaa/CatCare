package ru.kpfu.itis.gureva.catcare.di

import dagger.Component
import ru.kpfu.itis.gureva.catcare.data.di.DataBinderModule
import ru.kpfu.itis.gureva.catcare.data.di.NetworkModule
import ru.kpfu.itis.gureva.catcare.presentation.di.ViewModelBinderModule
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.CatFactFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataBinderModule::class, ViewModelBinderModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(fragment: HelpfulFragment)

    fun inject(fragment: CatFactFragment)
}

