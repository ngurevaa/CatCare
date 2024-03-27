package ru.kpfu.itis.gureva.catcare.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.kpfu.itis.gureva.catcare.data.repository.CatFactRepositoryImpl
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository
import ru.kpfu.itis.gureva.catcare.presentation.ui.helpful.HelpfulViewModel

@Module
interface DataModuleBinder {

    @Binds
    fun bindCatFactRepository_to_CatFactRepositoryImpl(repositoryImpl: CatFactRepositoryImpl): CatFactRepository

    @Binds
    @IntoMap
    @ViewModelKey(HelpfulViewModel::class)
    fun bindHelpfulViewModel(viewModel: HelpfulViewModel): ViewModel

    @Binds
    fun bindMultiViewModelFactory_to_ViewModelFactory(factory: MultiViewModelFactory): ViewModelProvider.Factory
}
