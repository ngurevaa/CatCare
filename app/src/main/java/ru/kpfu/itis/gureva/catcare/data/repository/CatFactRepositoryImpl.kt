package ru.kpfu.itis.gureva.catcare.data.repository

import ru.kpfu.itis.gureva.catcare.data.remote.CatFactsApi
import ru.kpfu.itis.gureva.catcare.data.remote.response.CatFactResponse
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository
import javax.inject.Inject

class CatFactRepositoryImpl @Inject constructor(
    private val api: CatFactsApi
) : CatFactRepository {

    override suspend fun getFact(): CatFactResponse {
        return api.getFact()
    }
}
