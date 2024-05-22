package ru.kpfu.itis.gureva.catcare.presentation.mapper

import ru.kpfu.itis.gureva.catcare.domain.model.WelcomeDomainModel
import ru.kpfu.itis.gureva.catcare.presentation.model.WelcomeUIModel
import javax.inject.Inject

class WelcomeUIModelMapper @Inject constructor() {
    fun mapWelcomeDomainModelToWelcomeUIModel(domainModel: WelcomeDomainModel): WelcomeUIModel {
        return WelcomeUIModel(domainModel.title, domainModel.description, domainModel.image)
    }
}
