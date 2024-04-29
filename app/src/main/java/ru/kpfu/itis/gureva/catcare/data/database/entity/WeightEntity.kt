package ru.kpfu.itis.gureva.catcare.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "weight",
    foreignKeys = [ForeignKey(entity = PetEntity::class,
                              parentColumns = arrayOf("id"),
                              childColumns = arrayOf("petId"),
                              onDelete = ForeignKey.CASCADE)])
data class WeightEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val weight: Float,
    val date: String,
    val petId: Int
)
