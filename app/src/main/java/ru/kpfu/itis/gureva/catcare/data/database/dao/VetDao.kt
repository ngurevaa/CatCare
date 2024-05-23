package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity

@Dao
interface VetDao {
    @Query("SELECT * FROM vet WHERE petId = :id")
    fun getAllByPetId(id: Int): LiveData<List<VetEntity>>

    @Insert
    fun save(vetEntity: VetEntity)

    @Delete
    fun delete(vetEntity: VetEntity)
}
