package ru.kpfu.itis.gureva.catcare.domain.repository

import ru.kpfu.itis.gureva.catcare.domain.model.CatFactDomainModel

interface CatFactRepository {

    suspend fun getFact(): CatFactDomainModel
}
