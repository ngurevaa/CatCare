package ru.kpfu.itis.gureva.catcare.data.repository

import ru.kpfu.itis.gureva.catcare.domain.model.NewsDomainModel
import ru.kpfu.itis.gureva.catcare.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {
    override fun getNews(): List<NewsDomainModel> = news

    private val news = listOf(
        NewsDomainModel(1, "Как понять что ваш кот толстый?",
            "Полненькие питомцы выглядят умилительно. Однако не нужно забывать: лишний вес — это лишние проблемы со здоровьем вашего кота. " +
                    "Как минимум: с дыханием, суставами и работой сердечно-сосудистой системы. Когда у кошки нормальный вес, вы можете видеть, как ваше животное дышит, по колебаниям рёбер. " +
                    "А вот когда питомец начал «расползаться», вам придётся хорошенько присмотреться, чтобы увидеть процесс дыхания. " +
                    "Рёбра вашей кошки должны хорошо прощупываться. Посмотрите на кошку, лежащую на боку. Если вы не видите чёткого перехода от её живота к бедрам, значит, ваш питомец точно пополнел.", "https://koshka.top/uploads/posts/2021-12/1639983846_55-koshka-top-p-ochen-tolstaya-koshka-58.jpg")
    )
}
