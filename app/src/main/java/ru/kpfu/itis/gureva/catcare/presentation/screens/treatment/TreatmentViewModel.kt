package ru.kpfu.itis.gureva.catcare.presentation.screens.treatment

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
import ru.kpfu.itis.gureva.catcare.data.database.entity.TreatmentEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.TreatmentRepository
import ru.kpfu.itis.gureva.catcare.data.database.repository.VaccinationRepository
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat

class TreatmentViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val treatmentRepository: TreatmentRepository
) : ViewModel() {
    val treatments: LiveData<List<TreatmentEntity>> = treatmentRepository.getAllByPetId(petId).map {
        val dateFormat = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME)
        it.sortedByDescending { item ->
            dateFormat.parse(item.date)?.time
        }
    }

    fun removeItem(position: Int) {
        viewModelScope.launch {
            treatments.value?.get(position)?.let { treatmentRepository.delete(it) }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): TreatmentViewModel
    }
}
