package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import java.time.LocalDate

@Dao
interface PetDao {
    @Insert
    fun save(pet: PetEntity)

    @Query("SELECT * FROM pet WHERE id = :id")
    fun getById(id: Int): PetEntity?
}
