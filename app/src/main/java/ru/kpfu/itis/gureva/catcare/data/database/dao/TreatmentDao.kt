package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.TreatmentEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity

@Dao
interface TreatmentDao {
    @Query("SELECT * FROM treatment WHERE petId = :id")
    fun getAllByPetId(id: Int): LiveData<List<TreatmentEntity>>

    @Insert
    fun save(treatmentEntity: TreatmentEntity)

    @Delete
    fun delete(treatmentEntity: TreatmentEntity)
}
