package ru.kpfu.itis.gureva.catcare.domain.mapper

import ru.kpfu.itis.gureva.catcare.data.remote.response.CatFactResponse
import ru.kpfu.itis.gureva.catcare.domain.model.CatFactDomainModel
import javax.inject.Inject

class CatFactDomainModelMapper @Inject constructor() {

    fun mapResponseToDomainModel(response: CatFactResponse): CatFactDomainModel {
        return CatFactDomainModel(response.data[0])
    }
}
