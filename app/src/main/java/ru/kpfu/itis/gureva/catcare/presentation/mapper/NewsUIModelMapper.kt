package ru.kpfu.itis.gureva.catcare.presentation.mapper

import ru.kpfu.itis.gureva.catcare.domain.model.NewsDomainModel
import ru.kpfu.itis.gureva.catcare.presentation.model.NewsUIModel
import javax.inject.Inject

class NewsUIModelMapper @Inject constructor() {
    fun mapDomainModelToUIModel(domainModel: NewsDomainModel): NewsUIModel {
        return NewsUIModel(domainModel.id, domainModel.title, domainModel.description, domainModel.image)
    }
}
