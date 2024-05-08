package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity

@Dao
interface BehaviourDao {
    @Query("SELECT * FROM behaviour WHERE petId = :id")
    fun getAllByPetId(id: Int): LiveData<List<BehaviourEntity>>

    @Insert
    fun save(behaviourEntity: BehaviourEntity)

    @Delete
    fun delete(behaviourEntity: BehaviourEntity)
}
