package ru.kpfu.itis.gureva.catcare.data.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.VaccinationDao
import ru.kpfu.itis.gureva.catcare.data.database.dao.VetDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import javax.inject.Inject

class VetRepository @Inject constructor(
    private val vetDao: VetDao
) {
    fun getAllByPetId(id: Int): LiveData<List<VetEntity>> = vetDao.getAllByPetId(id)

    suspend fun save(vetEntity: VetEntity) {
        withContext(Dispatchers.IO) {
            vetDao.save(vetEntity)
        }
    }

    suspend fun delete(vetEntity: VetEntity) {
        withContext(Dispatchers.IO) {
            vetDao.delete(vetEntity)
        }
    }
}
