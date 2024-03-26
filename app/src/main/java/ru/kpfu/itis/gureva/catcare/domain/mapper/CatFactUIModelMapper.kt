package ru.kpfu.itis.gureva.catcare.domain.mapper

import ru.kpfu.itis.gureva.catcare.domain.model.CatFactDomainModel
import ru.kpfu.itis.gureva.catcare.presentation.model.CatFactUIModel

class CatFactUIModelMapper {

    fun mapDomainModelToUIModel(domainModel: CatFactDomainModel): CatFactUIModel {
        return CatFactUIModel(domainModel.fact)
    }
}
