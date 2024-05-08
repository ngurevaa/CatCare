package ru.kpfu.itis.gureva.catcare.presentation.screens.unusual

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.BehaviourRepository
import ru.kpfu.itis.gureva.catcare.data.database.repository.WeightRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.WeightControlViewModel
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat

class UnusualBehaviourViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val behaviourRepository: BehaviourRepository
) : ViewModel() {
    val behaviours: LiveData<List<BehaviourEntity>> = behaviourRepository.getAllByPetId(petId).map {
        val dateFormat = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME)
        it.sortedByDescending { item ->
            dateFormat.parse(item.date)?.time
        }
    }

    fun removeItem(position: Int) {
        viewModelScope.launch {
            behaviours.value?.get(position)?.let { behaviourRepository.delete(it) }
        }
    }
    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): UnusualBehaviourViewModel
    }
}
