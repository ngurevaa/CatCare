package ru.kpfu.itis.gureva.catcare.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.gureva.catcare.data.mapper.CatFactDomainModelMapper
import ru.kpfu.itis.gureva.catcare.data.remote.CatFactsApi
import ru.kpfu.itis.gureva.catcare.data.repository.CatFactRepositoryImpl
import ru.kpfu.itis.gureva.catcare.domain.mapper.CatFactUIModelMapper
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetCatFactUseCase

object ServiceLocator {
    private val httpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://meowfacts.herokuapp.com")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val weatherApi by lazy {
        retrofit.create(CatFactsApi::class.java)
    }

    private val catFactDomainModelMapper by lazy { CatFactDomainModelMapper() }

    private val catFactUIModelMapper by lazy { CatFactUIModelMapper() }

    private val catFactRepository by lazy { CatFactRepositoryImpl(weatherApi, catFactDomainModelMapper) }

    val getCatFactUseCase by lazy {
        GetCatFactUseCase(catFactRepository, catFactUIModelMapper) }
}
