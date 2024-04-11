package ru.kpfu.itis.gureva.catcare.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.domain.mapper.CatFactDomainModelMapper
import ru.kpfu.itis.gureva.catcare.domain.model.CatFactDomainModel
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository
import javax.inject.Inject

class GetCatFactUseCase @Inject constructor(
    private val repository: CatFactRepository,
    private val mapper: CatFactDomainModelMapper
) {
    suspend operator fun invoke(): CatFactDomainModel {
        return withContext(Dispatchers.IO) {
            mapper.mapResponseToDomainModel(repository.getFact())
        }
    }
}
