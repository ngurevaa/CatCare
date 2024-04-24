package ru.kpfu.itis.gureva.catcare.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.HelpfulViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.pets.MyPetsViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileEditingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileViewModel

@Module
interface ViewModelBinderModule {
    @Binds
    fun bindMultiViewModelFactory_to_ViewModelFactory(factory: MultiViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HelpfulViewModel::class)
    fun bindHelpfulViewModel(viewModel: HelpfulViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyPetsViewModel::class)
    fun bindMyPetsViewModel(viewModel: MyPetsViewModel): ViewModel
}
