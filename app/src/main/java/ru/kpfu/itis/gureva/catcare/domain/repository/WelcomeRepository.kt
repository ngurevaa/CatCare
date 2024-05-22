package ru.kpfu.itis.gureva.catcare.domain.repository

import ru.kpfu.itis.gureva.catcare.domain.model.WelcomeDomainModel

interface WelcomeRepository {
    fun getWelcome() : List<WelcomeDomainModel>
}
