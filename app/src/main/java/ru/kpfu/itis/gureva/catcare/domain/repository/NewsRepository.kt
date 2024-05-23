package ru.kpfu.itis.gureva.catcare.domain.repository

import ru.kpfu.itis.gureva.catcare.domain.model.NewsDomainModel

interface NewsRepository {
    fun getNews(): List<NewsDomainModel>
}
