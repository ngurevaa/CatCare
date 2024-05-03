package ru.kpfu.itis.gureva.catcare.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kpfu.itis.gureva.catcare.data.database.dao.PetDao
import ru.kpfu.itis.gureva.catcare.data.database.dao.WeightDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity

@Database(entities = [PetEntity::class, WeightEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val petDao: PetDao
    abstract val weightDao: WeightDao
}
