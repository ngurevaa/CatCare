package ru.kpfu.itis.gureva.catcare.data.repository

import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.domain.model.WelcomeDomainModel
import ru.kpfu.itis.gureva.catcare.domain.repository.WelcomeRepository
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import javax.inject.Inject

class WelcomeRepositoryImpl @Inject constructor(
    resourceManager: ResourceManager
) : WelcomeRepository {
    override fun getWelcome(): List<WelcomeDomainModel> = list

    private val list: List<WelcomeDomainModel> = listOf(
        WelcomeDomainModel(
            1,
            resourceManager.getString(R.string.welcome_title_1),
            resourceManager.getString(R.string.welcome_description_1),
            R.drawable.hello_cat
        ),
        WelcomeDomainModel(
            2,
            resourceManager.getString(R.string.welcome_title_2),
            resourceManager.getString(R.string.welcome_description_2),
            R.drawable.cat_with_phone
        ),
        WelcomeDomainModel(
            3,
            resourceManager.getString(R.string.welcome_title_3),
            resourceManager.getString(R.string.welcome_description_3),
            R.drawable.cute_cat
        )
    )
}
