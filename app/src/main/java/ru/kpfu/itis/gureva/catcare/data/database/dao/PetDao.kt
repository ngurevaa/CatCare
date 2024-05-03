package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(pet: PetEntity): Long

    @Query("SELECT * FROM pet WHERE id = :id")
    fun getById(id: Int): PetEntity?

    @Query("SELECT * FROM pet")
    fun getAll(): List<PetEntity>?

    @Update
    fun update(pet: PetEntity)
}
