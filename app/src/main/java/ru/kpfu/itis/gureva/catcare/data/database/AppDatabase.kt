package ru.kpfu.itis.gureva.catcare.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.kpfu.itis.gureva.catcare.data.database.converter.BehaviourEnumConverter
import ru.kpfu.itis.gureva.catcare.data.database.dao.BehaviourDao
import ru.kpfu.itis.gureva.catcare.data.database.dao.PetDao
import ru.kpfu.itis.gureva.catcare.data.database.dao.WeightDao
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity

@Database(entities = [PetEntity::class, WeightEntity::class, BehaviourEntity::class], version = 1)
@TypeConverters(BehaviourEnumConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val petDao: PetDao
    abstract val weightDao: WeightDao
    abstract val behaviourDao: BehaviourDao
}
