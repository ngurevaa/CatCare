package ru.kpfu.itis.gureva.catcare.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "treatment",
    foreignKeys = [ForeignKey(entity = PetEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("petId"),
        onDelete = ForeignKey.CASCADE)])
data class TreatmentEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val description: String,
    val date: String,
    val petId: Int
)
