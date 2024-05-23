package ru.kpfu.itis.gureva.catcare.data.di

import dagger.Binds
import dagger.Module
import ru.kpfu.itis.gureva.catcare.data.repository.CatFactRepositoryImpl
import ru.kpfu.itis.gureva.catcare.data.repository.NewsRepositoryImpl
import ru.kpfu.itis.gureva.catcare.data.repository.WelcomeRepositoryImpl
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository
import ru.kpfu.itis.gureva.catcare.domain.repository.NewsRepository
import ru.kpfu.itis.gureva.catcare.domain.repository.WelcomeRepository

@Module
interface DataBinderModule {

    @Binds
    fun bindCatFactRepositoryToCatFactRepositoryImpl(repositoryImpl: CatFactRepositoryImpl): CatFactRepository

    @Binds
    fun bindNewsRepositoryToNewsRepositoryImpl(repositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    fun bindWelcomeRepositoryToWelcomeRepositoryImpl(repositoryImpl: WelcomeRepositoryImpl): WelcomeRepository
}
