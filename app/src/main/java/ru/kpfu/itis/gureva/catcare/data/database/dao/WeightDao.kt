package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight WHERE petId = :id")
    fun getAllByPetId(id: Int): Flow<List<WeightEntity>>

    @Insert
    fun save(weightEntity: WeightEntity)
}
