package ru.kpfu.itis.gureva.catcare.data.database.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.WeightDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import javax.inject.Inject

class WeightRepository @Inject constructor(
    private val weightDao: WeightDao
) {
    fun getAllByPetId(id: Int): Flow<List<WeightEntity>> = weightDao.getAllByPetId(id)

    suspend fun save(weightEntity: WeightEntity) {
        withContext(Dispatchers.IO) {
            weightDao.save(weightEntity)
        }
    }
}
