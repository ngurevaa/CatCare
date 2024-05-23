package ru.kpfu.itis.gureva.catcare.data.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gureva.catcare.data.database.dao.DiaryDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.DiaryEntity
import javax.inject.Inject

class DiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao
) {
    fun getAll(): LiveData<List<DiaryEntity>> = diaryDao.getAll()

    suspend fun save(diaryEntity: DiaryEntity): Long {
        return withContext(Dispatchers.IO) {
            diaryDao.save(diaryEntity)
        }
    }

    suspend fun delete(diaryEntity: DiaryEntity) {
        withContext(Dispatchers.IO) {
            diaryDao.delete(diaryEntity)
        }
    }
}
