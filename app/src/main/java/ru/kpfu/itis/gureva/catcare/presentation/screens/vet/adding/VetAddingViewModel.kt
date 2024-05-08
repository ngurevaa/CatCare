package ru.kpfu.itis.gureva.catcare.presentation.screens.vet.adding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.VaccinationRepository
import ru.kpfu.itis.gureva.catcare.data.database.repository.VetRepository
import ru.kpfu.itis.gureva.catcare.data.database.repository.WeightRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.weight.adding.WeightAddingViewModel

class VetAddingViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val vetRepository: VetRepository
) : ViewModel() {

    fun save(description: String, date: String) {
        viewModelScope.launch {
            vetRepository.save(VetEntity(null, description, date, petId))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): VetAddingViewModel
    }
}
