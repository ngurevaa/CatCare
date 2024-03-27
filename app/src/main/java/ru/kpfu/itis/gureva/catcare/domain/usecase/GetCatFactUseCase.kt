package ru.kpfu.itis.gureva.catcare.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.domain.mapper.CatFactUIModelMapper
import ru.kpfu.itis.gureva.catcare.domain.repository.CatFactRepository
import ru.kpfu.itis.gureva.catcare.presentation.model.CatFactUIModel
import javax.inject.Inject

class GetCatFactUseCase @Inject constructor(
    private val repository: CatFactRepository,
    private val mapper: CatFactUIModelMapper
) {
    suspend operator fun invoke(): CatFactUIModel {
        return withContext(Dispatchers.IO) {
            mapper.mapDomainModelToUIModel(repository.getFact())
        }
    }
}
