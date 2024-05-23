package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight WHERE petId = :id")
    fun getAllByPetId(id: Int): LiveData<List<WeightEntity>>

    @Insert
    fun save(weightEntity: WeightEntity)

    @Delete
    fun delete(weightEntity: WeightEntity)
}
