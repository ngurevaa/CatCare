package ru.kpfu.itis.gureva.catcare.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kpfu.itis.gureva.catcare.presentation.screens.diary.DiaryViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.diary.adding.DiaryAddingViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.fact.CatFactViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.helpful.HelpfulViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.pets.MyPetsViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.registration.WelcomeViewModel

@Module
interface ViewModelBinderModule {
    @Binds
    fun bindMultiViewModelFactoryToViewModelFactory(factory: MultiViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CatFactViewModel::class)
    fun bindCatFactViewModel(viewModel: CatFactViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyPetsViewModel::class)
    fun bindMyPetsViewModel(viewModel: MyPetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiaryViewModel::class)
    fun bindDiaryViewModel(viewModel: DiaryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiaryAddingViewModel::class)
    fun bindDiaryAddingViewModel(viewModel: DiaryAddingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HelpfulViewModel::class)
    fun bindHelpfulViewModel(viewModel: HelpfulViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel
}
