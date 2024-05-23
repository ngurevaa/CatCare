package ru.kpfu.itis.gureva.catcare.data.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.TreatmentDao
import ru.kpfu.itis.gureva.catcare.data.database.dao.VaccinationDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.TreatmentEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import javax.inject.Inject

class TreatmentRepository @Inject constructor(
    private val treatmentDao: TreatmentDao
) {
    fun getAllByPetId(id: Int): LiveData<List<TreatmentEntity>> = treatmentDao.getAllByPetId(id)

    suspend fun save(treatmentEntity: TreatmentEntity) {
        withContext(Dispatchers.IO) {
            treatmentDao.save(treatmentEntity)
        }
    }

    suspend fun delete(treatmentEntity: TreatmentEntity) {
        withContext(Dispatchers.IO) {
            treatmentDao.delete(treatmentEntity)
        }
    }
}
