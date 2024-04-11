package ru.kpfu.itis.gureva.catcare.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import javax.inject.Inject

class PetProfileViewModel @Inject constructor(
    private val petRepository: PetRepository
) : ViewModel() {

    fun getPet(): Flow<PetEntity?> = petRepository.getById(1)

    fun savePet(pet: PetEntity) {
        viewModelScope.launch {
            petRepository.save(pet)
        }
    }

    fun updatePet(pet: PetEntity) {
        viewModelScope.launch {
            petRepository.update(pet)
        }
    }
}
