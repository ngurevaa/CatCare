package ru.kpfu.itis.gureva.catcare.di

import dagger.Component
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModuleBinder::class])
interface AppComponent {

    fun inject(fragment: HelpfulFragment)
}

