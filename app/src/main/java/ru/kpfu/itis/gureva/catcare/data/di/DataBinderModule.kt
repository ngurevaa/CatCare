package ru.kpfu.itis.gureva.catcare.data.di

import dagger.Binds
import dagger.Module
import ru.kpfu.itis.gureva.catcare.data.repository.CatFactRepositoryImpl
import ru.kpfu.itis.gureva.catcare.data.repository.NewsRepositoryImpl
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository
import ru.kpfu.itis.gureva.catcare.domain.repository.NewsRepository

@Module
interface DataBinderModule {

    @Binds
    fun bindCatFactRepositoryToCatFactRepositoryImpl(repositoryImpl: CatFactRepositoryImpl): CatFactRepository

    @Binds
    fun bindNewsRepositoryToNewsRepositoryImpl(repositoryImpl: NewsRepositoryImpl): NewsRepository
}
