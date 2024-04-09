package ru.kpfu.itis.gureva.catcare.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "birth_day")
    val birthDay: String,
    val breed: String,
    val gender: String
)
