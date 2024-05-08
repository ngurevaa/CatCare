package ru.kpfu.itis.gureva.catcare.data.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.VaccinationDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import javax.inject.Inject

class VaccinationRepository @Inject constructor(
    private val vaccinationDao: VaccinationDao
) {
    fun getAllByPetId(id: Int): LiveData<List<VaccinationEntity>> = vaccinationDao.getAllByPetId(id)

    suspend fun save(vaccinationEntity: VaccinationEntity) {
        withContext(Dispatchers.IO) {
            vaccinationDao.save(vaccinationEntity)
        }
    }

    suspend fun delete(vaccinationEntity: VaccinationEntity) {
        withContext(Dispatchers.IO) {
            vaccinationDao.delete(vaccinationEntity)
        }
    }
}
