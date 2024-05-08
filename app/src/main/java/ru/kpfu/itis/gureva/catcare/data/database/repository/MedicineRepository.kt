package ru.kpfu.itis.gureva.catcare.data.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.MedicineDao
import ru.kpfu.itis.gureva.catcare.data.database.dao.VaccinationDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.MedicineEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val medicineDao: MedicineDao
) {
    fun getAllByPetId(id: Int): LiveData<List<MedicineEntity>> = medicineDao.getAllByPetId(id)

    suspend fun save(medicineEntity: MedicineEntity) {
        withContext(Dispatchers.IO) {
            medicineDao.save(medicineEntity)
        }
    }

    suspend fun delete(medicineEntity: MedicineEntity) {
        withContext(Dispatchers.IO) {
            medicineDao.delete(medicineEntity)
        }
    }
}
