package ru.kpfu.itis.gureva.catcare.data.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.BehaviourDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import javax.inject.Inject

class BehaviourRepository @Inject constructor(
    private val behaviourDao: BehaviourDao
) {
    fun getAllByPetId(id: Int): LiveData<List<BehaviourEntity>> = behaviourDao.getAllByPetId(id)

    suspend fun save(behaviourEntity: BehaviourEntity) {
        withContext(Dispatchers.IO) {
            behaviourDao.save(behaviourEntity)
        }
    }

    suspend fun delete(behaviourEntity: BehaviourEntity) {
        withContext(Dispatchers.IO) {
            behaviourDao.delete(behaviourEntity)
        }
    }
}
