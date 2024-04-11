package ru.kpfu.itis.gureva.catcare.data.database.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.PetDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petDao: PetDao
) {
    fun getById(id: Int): Flow<PetEntity?> {
        return petDao.getById(id)
    }

    suspend fun save(pet: PetEntity) {
        withContext(Dispatchers.IO) {
            petDao.save(pet)
        }
    }

    suspend fun update(pet: PetEntity) {
        withContext(Dispatchers.IO) {
            petDao.update(pet.id, pet.name, pet.birthDay, pet.breed, pet.gender, pet.image)
        }
    }
}
