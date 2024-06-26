package ru.kpfu.itis.gureva.catcare.presentation.screens.medicine

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
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.MedicineRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseViewModel
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat

class MedicineViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val medicineRepository: MedicineRepository
) : BaseViewModel() {
    val medicines: LiveData<List<MedicineEntity>> = medicineRepository.getAllByPetId(petId).map {
        val dateFormat = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME)
        it.sortedByDescending { item ->
            dateFormat.parse(item.date)?.time
        }
    }

    private var removedItem: MedicineEntity? = null

    override fun removeItem(position: Int) {
        viewModelScope.launch {
            removedItem = medicines.value?.get(position)
            removedItem?.let {
                medicineRepository.delete(it)
            }
        }
    }

    override fun returnItem() {
        viewModelScope.launch {
            removedItem?.let {
                medicineRepository.save(it)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): MedicineViewModel
    }
}
