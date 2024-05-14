package ru.kpfu.itis.gureva.catcare.presentation.screens.weight

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.MedicineEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import ru.kpfu.itis.gureva.catcare.data.database.repository.WeightRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseViewModel
import ru.kpfu.itis.gureva.catcare.presentation.screens.profile.PetProfileViewModel
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat

class WeightControlViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val weightRepository: WeightRepository
) : BaseViewModel() {

    val weights: LiveData<List<WeightEntity>> = weightRepository.getAllByPetId(petId).map {
        val dateFormat = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME)
        it.sortedByDescending { item ->
            dateFormat.parse(item.date)?.time
        }
    }

    private var removedItem: WeightEntity? = null

    override fun removeItem(position: Int) {
        viewModelScope.launch {
            removedItem = weights.value?.get(position)
            removedItem?.let { weightRepository.delete(it) }
        }
    }

    override fun returnItem() {
        viewModelScope.launch {
            removedItem?.let {
                weightRepository.save(it)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): WeightControlViewModel
    }
}
