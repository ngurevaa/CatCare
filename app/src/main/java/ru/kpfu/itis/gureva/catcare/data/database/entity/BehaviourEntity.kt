package ru.kpfu.itis.gureva.catcare.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.kpfu.itis.gureva.catcare.utils.Behaviour

@Entity(tableName = "behaviour",
        foreignKeys = [ForeignKey(entity = PetEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("petId"),
            onDelete = ForeignKey.CASCADE)])
data class BehaviourEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val petId: Int,
    val behaviour: Behaviour,
    val description: String,
    val date: String
)
