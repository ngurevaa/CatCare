package ru.kpfu.itis.gureva.catcare.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.kpfu.itis.gureva.catcare.data.di.DataBinderModule
import ru.kpfu.itis.gureva.catcare.data.di.NetworkModule
import ru.kpfu.itis.gureva.catcare.data.di.DataModule
import ru.kpfu.itis.gureva.catcare.presentation.MainActivity
import ru.kpfu.itis.gureva.catcare.presentation.di.ViewModelBinderModule
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.CatFactFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.HelpfulFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.pets.MyPetsFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileEditingFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileEditingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileFragment
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileViewModel
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

    fun getPetProfileViewModel(): PetProfileViewModel.Factory
    fun getPetProfileEditingViewModel(): PetProfileEditingViewModel.Factory

    fun inject(activity: MainActivity)
    fun inject(fragment: HelpfulFragment)
    fun inject(fragment: CatFactFragment)
    fun inject(fragment: PetProfileEditingFragment)
    fun inject(fragment: MyPetsFragment)
}

