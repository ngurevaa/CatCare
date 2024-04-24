package ru.kpfu.itis.gureva.catcare.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.base.Keys
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import ru.kpfu.itis.gureva.catcare.utils.Formatter

class PetProfileViewModel @AssistedInject constructor(
    private val petRepository: PetRepository,
    @Assisted(value = Keys.PET_ID) private val petId: Int
) : ViewModel() {
    private val _pet = MutableLiveData<PetEntity?>()
    val pet: LiveData<PetEntity?>
        get() = _pet

    private val _age = MutableLiveData<String>()
    val age: LiveData<String>
        get() = _age

    init {
        viewModelScope.launch {
            _pet.value = petRepository.getById(petId)

            formatAge()
        }
    }

    private fun formatAge() {
        _age.value = Formatter.findDifferenceBetweenDays(pet.value?.birthDay)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.PET_ID) petId: Int): PetProfileViewModel
    }
}
