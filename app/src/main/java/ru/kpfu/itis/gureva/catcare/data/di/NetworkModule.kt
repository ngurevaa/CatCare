package ru.kpfu.itis.gureva.catcare.data.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.gureva.catcare.BuildConfig
import ru.kpfu.itis.gureva.catcare.data.remote.CatFactsApi
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.CAT_FACT_API_BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideCatFactApi(
        retrofit: Retrofit
    ): CatFactsApi = retrofit.create(CatFactsApi::class.java)
}
