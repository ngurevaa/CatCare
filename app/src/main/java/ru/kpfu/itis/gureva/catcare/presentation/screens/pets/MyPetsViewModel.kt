package ru.kpfu.itis.gureva.catcare.presentation.screens.pets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import javax.inject.Inject

class MyPetsViewModel @Inject constructor(
    private val petRepository: PetRepository
) : ViewModel() {
    private val _pets = MutableLiveData<List<PetEntity>>()
    val pets: LiveData<List<PetEntity>>
        get() = _pets

    init {
        viewModelScope.launch {
            _pets.value = petRepository.getAll()
        }
    }
}
