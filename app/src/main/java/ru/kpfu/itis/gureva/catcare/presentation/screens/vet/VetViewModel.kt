package ru.kpfu.itis.gureva.catcare.presentation.screens.vet

import android.util.Log
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
import ru.kpfu.itis.gureva.catcare.data.database.entity.MedicineEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VaccinationEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.VetEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.VaccinationRepository
import ru.kpfu.itis.gureva.catcare.data.database.repository.VetRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseViewModel
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat

class VetViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val vetRepository: VetRepository
) : BaseViewModel() {
    val vets: LiveData<List<VetEntity>> = vetRepository.getAllByPetId(petId).map {
        val dateFormat = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME)
        it.sortedByDescending { item ->
            dateFormat.parse(item.date)?.time
        }
    }

    private var removedItem: VetEntity? = null

    override fun removeItem(position: Int) {
        viewModelScope.launch {
            removedItem = vets.value?.get(position)
            removedItem?.let { vetRepository.delete(it) }
        }
    }

    override fun returnItem() {
        viewModelScope.launch {
            removedItem?.let {
                vetRepository.save(it)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): VetViewModel
    }
}
