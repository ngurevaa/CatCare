package ru.kpfu.itis.gureva.catcare.data.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.WeightDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import javax.inject.Inject

class WeightRepository @Inject constructor(
    private val weightDao: WeightDao
) {
    fun getAllByPetId(id: Int): LiveData<List<WeightEntity>> = weightDao.getAllByPetId(id)

    suspend fun save(weightEntity: WeightEntity) {
        withContext(Dispatchers.IO) {
            weightDao.save(weightEntity)
        }
    }

    suspend fun delete(weightEntity: WeightEntity) {
        withContext(Dispatchers.IO) {
            weightDao.delete(weightEntity)
        }
    }
}
