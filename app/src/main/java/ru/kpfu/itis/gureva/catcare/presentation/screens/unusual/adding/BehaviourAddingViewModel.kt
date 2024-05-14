package ru.kpfu.itis.gureva.catcare.presentation.screens.unusual.adding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.BehaviourEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.BehaviourRepository
import ru.kpfu.itis.gureva.catcare.utils.Behaviour
import java.io.Serializable

class BehaviourAddingViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val behaviourRepository: BehaviourRepository
) : ViewModel() {

    fun save(behaviour: Behaviour, description: String, date: String) {
        viewModelScope.launch {
            behaviourRepository.save(BehaviourEntity(null, petId, behaviour, description, date))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): BehaviourAddingViewModel
    }
}
