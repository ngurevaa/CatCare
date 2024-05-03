package ru.kpfu.itis.gureva.catcare.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var name: String,
    @ColumnInfo(name = "birth_day")
    var birthDay: String,
    var breed: String,
    var gender: String,
    var image: String?
)
