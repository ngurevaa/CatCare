package ru.kpfu.itis.gureva.catcare.presentation.screens.treatment.adding

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.TreatmentEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.TreatmentRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseAddingViewModel

class TreatmentAddingViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val treatmentRepository: TreatmentRepository
) : BaseAddingViewModel() {

    override fun save(description: String, date: String) {
        viewModelScope.launch {
            treatmentRepository.save(TreatmentEntity(null, description, date, petId))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): TreatmentAddingViewModel
    }
}
