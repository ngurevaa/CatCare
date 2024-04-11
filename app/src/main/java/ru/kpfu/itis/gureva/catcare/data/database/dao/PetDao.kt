package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity

@Dao
interface PetDao {
    @Insert
    fun save(pet: PetEntity)

    @Query("SELECT * FROM pet WHERE id = :id")
    fun getById(id: Int): Flow<PetEntity?>

    @Query("UPDATE pet SET name = :name, birth_day = :birthDay, breed = :breed, " +
            "gender = :gender, image = :image WHERE id = :id")
    fun update(id: Int, name: String, birthDay: String, breed: String, gender: String, image: String?)
}
