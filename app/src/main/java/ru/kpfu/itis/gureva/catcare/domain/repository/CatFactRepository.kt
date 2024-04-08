package ru.kpfu.itis.gureva.catcare.domain.repository

import ru.kpfu.itis.gureva.catcare.data.remote.response.CatFactResponse

interface CatFactRepository {

    suspend fun getFact(): CatFactResponse
}
