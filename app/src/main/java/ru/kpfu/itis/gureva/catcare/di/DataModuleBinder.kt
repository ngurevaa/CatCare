package ru.kpfu.itis.gureva.catcare.di

import dagger.Binds
import dagger.Module
import ru.kpfu.itis.gureva.catcare.data.repository.CatFactRepositoryImpl
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository

@Module
interface DataModuleBinder {

    @Binds
    fun bindCatFactRepository_to_CatFactRepositoryImpl(repositoryImpl: CatFactRepositoryImpl): CatFactRepository
}
