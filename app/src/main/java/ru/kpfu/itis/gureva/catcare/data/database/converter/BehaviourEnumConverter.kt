package ru.kpfu.itis.gureva.catcare.data.database.converter

import androidx.room.TypeConverter
import ru.kpfu.itis.gureva.catcare.utils.Behaviour
import javax.inject.Inject

class BehaviourEnumConverter @Inject constructor() {
    @TypeConverter
    fun from(behaviour: Behaviour): String {
        return behaviour.name
    }

    @TypeConverter
    fun to(behaviour: String): Behaviour {
        return enumValueOf(behaviour)
    }
}
