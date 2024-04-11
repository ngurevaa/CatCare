package ru.kpfu.itis.gureva.catcare.data.remote

import retrofit2.http.GET
import ru.kpfu.itis.gureva.catcare.data.remote.response.CatFactResponse

interface CatFactsApi {

    @GET("/")
    suspend fun getFact(): CatFactResponse
}
