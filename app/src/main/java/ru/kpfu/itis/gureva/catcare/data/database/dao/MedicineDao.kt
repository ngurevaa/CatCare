package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.MedicineEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicine WHERE petId = :id")
    fun getAllByPetId(id: Int): LiveData<List<MedicineEntity>>

    @Insert
    fun save(medicineEntity: MedicineEntity)

    @Delete
    fun delete(medicineEntity: MedicineEntity)
}
