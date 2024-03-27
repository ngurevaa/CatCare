package ru.kpfu.itis.gureva.catcare.di

import dagger.Component
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulFragment
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModuleBinder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(fragment: HelpfulFragment)
}

