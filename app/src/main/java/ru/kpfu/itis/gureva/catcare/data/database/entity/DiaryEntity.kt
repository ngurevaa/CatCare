package ru.kpfu.itis.gureva.catcare.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val description: String,
    val image: String?,
    val date: String
)
