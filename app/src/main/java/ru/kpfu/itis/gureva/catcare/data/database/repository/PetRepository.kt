package ru.kpfu.itis.gureva.catcare.data.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.PetDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petDao: PetDao
) {
    suspend fun getById(id: Int): PetEntity? {
        return withContext(Dispatchers.IO) {
            petDao.getById(id)
        }
    }

    suspend fun save(pet: PetEntity) {
        withContext(Dispatchers.IO) {
            petDao.save(pet)
        }
    }

    suspend fun getAll(): List<PetEntity>? {
        return withContext(Dispatchers.IO) {
            petDao.getAll()
        }
    }
}
