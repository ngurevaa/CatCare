package ru.kpfu.itis.gureva.catcare.data.repository

import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.domain.model.NewsDomainModel
import ru.kpfu.itis.gureva.catcare.domain.repository.NewsRepository
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    resourceManager: ResourceManager
) : NewsRepository {
    override fun getNews(): List<NewsDomainModel> = news

    private val news = listOf(
        NewsDomainModel(1,
            resourceManager.getString(R.string.news_title_1),
            resourceManager.getString(R.string.news_description_1),
            "https://koshka.top/uploads/posts/2021-12/1639983846_55-koshka-top-p-ochen-tolstaya-koshka-58.jpg")
    )
}
