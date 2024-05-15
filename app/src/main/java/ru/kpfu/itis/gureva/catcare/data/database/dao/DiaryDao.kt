package ru.kpfu.itis.gureva.catcare.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.kpfu.itis.gureva.catcare.data.database.entity.DiaryEntity

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun getAll(): LiveData<List<DiaryEntity>>

    @Insert
    fun save(diaryEntity: DiaryEntity): Long

    @Delete
    fun delete(diaryEntity: DiaryEntity)
}
