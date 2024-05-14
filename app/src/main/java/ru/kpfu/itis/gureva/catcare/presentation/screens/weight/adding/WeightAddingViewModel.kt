package ru.kpfu.itis.gureva.catcare.presentation.screens.weight.adding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.base.Constants
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.WeightEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.WeightRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseAddingViewModel
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager

class WeightAddingViewModel @AssistedInject constructor(
    @Assisted(value = Keys.PET_ID) private val petId: Int,
    private val weightRepository: WeightRepository,
    private val resourceManager: ResourceManager
) : BaseAddingViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    override fun save(description: String, date: String) {
        if (checkFields(description)) {
            viewModelScope.launch {
                weightRepository.save(
                    WeightEntity(null, String.format("%.2f", description.toFloat())
                        .replace(',', '.').toFloat(), date, petId)
                )
            }
        }
    }

    private fun checkFields(weight: String): Boolean {
        if (weight.isEmpty()) {
            _error.value = resourceManager.getString(R.string.error_field_must_not_be_empty)
            return false
        }
        else if (weight.toFloat() == 0f) {
            _error.value = String.format(resourceManager.getString(R.string.weight_must_be_more_then), 0.0)
            return false;
        }
        else if (weight.toFloat() > Constants.MAX_PET_WEIGHT) {
            _error.value = String.format(resourceManager.getString(R.string.weight_must_be_less_then), Constants.MAX_PET_WEIGHT)
            return false
        }
        else {
            _error.value = null
            return true
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): WeightAddingViewModel
    }
}
