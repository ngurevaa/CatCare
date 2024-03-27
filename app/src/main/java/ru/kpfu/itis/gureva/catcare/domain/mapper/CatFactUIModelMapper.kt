package ru.kpfu.itis.gureva.catcare.domain.mapper

import ru.kpfu.itis.gureva.catcare.domain.model.CatFactDomainModel
import ru.kpfu.itis.gureva.catcare.presentation.model.CatFactUIModel
import javax.inject.Inject

class CatFactUIModelMapper @Inject constructor() {

    fun mapDomainModelToUIModel(domainModel: CatFactDomainModel): CatFactUIModel {
        return CatFactUIModel(domainModel.fact)
    }
}
