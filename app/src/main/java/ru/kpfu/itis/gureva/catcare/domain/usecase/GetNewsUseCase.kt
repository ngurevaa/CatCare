package ru.kpfu.itis.gureva.catcare.domain.usecase

import ru.kpfu.itis.gureva.catcare.domain.model.NewsDomainModel
import ru.kpfu.itis.gureva.catcare.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): List<NewsDomainModel> {
        return newsRepository.getNews()
    }
}
