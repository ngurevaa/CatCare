package ru.kpfu.itis.gureva.catcare.data.repository

import ru.kpfu.itis.gureva.catcare.data.mapper.CatFactDomainModelMapper
import ru.kpfu.itis.gureva.catcare.data.remote.CatFactsApi
import ru.kpfu.itis.gureva.catcare.domain.model.CatFactDomainModel
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository
import javax.inject.Inject

class CatFactRepositoryImpl @Inject constructor(
    private val api: CatFactsApi,
    private val mapper: CatFactDomainModelMapper
) : CatFactRepository {

    override suspend fun getFact(): CatFactDomainModel {
        return mapper.mapResponseToDomainModel(api.getFact())
    }
}
