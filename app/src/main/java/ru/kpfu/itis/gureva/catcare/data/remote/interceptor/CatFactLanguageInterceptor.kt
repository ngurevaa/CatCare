package ru.kpfu.itis.gureva.catcare.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.kpfu.itis.gureva.catcare.BuildConfig
import ru.kpfu.itis.gureva.catcare.base.Keys
import javax.inject.Inject

class CatFactLanguageInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url().newBuilder()
            .addQueryParameter(Keys.CAT_FACT_LANG_KEY, BuildConfig.CAT_FACT_LANG_VALUE)
            .build()

        val requestBuilder = chain.request().newBuilder().url(newUrl)
        return chain.proceed(requestBuilder.build())
    }
}
