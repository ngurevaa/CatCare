package ru.kpfu.itis.gureva.catcare.domain.usecase

import ru.kpfu.itis.gureva.catcare.domain.model.WelcomeDomainModel
import ru.kpfu.itis.gureva.catcare.domain.repository.WelcomeRepository
import javax.inject.Inject

class GetWelcomeUseCase @Inject constructor(
    private val welcomeRepository: WelcomeRepository
) {
    operator fun invoke(): List<WelcomeDomainModel> {
        return welcomeRepository.getWelcome()
    }
}
