package ru.kpfu.itis.gureva.catcare.presentation.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class PetProfileViewModel @Inject constructor(
    private val petRepository: PetRepository
) : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _breed = MutableLiveData<String>()
    val breed: LiveData<String>
        get() = _breed

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    private val _birthDay = MutableLiveData<String>()
    val birthDay: LiveData<String>
        get() = _birthDay

    private val _image = MutableLiveData<String?>()
    val image: LiveData<String?>
        get() = _image

    private var pet: PetEntity? =null

    private val _fieldError = MutableLiveData<String>()
    val fieldError: LiveData<String>
        get() = _fieldError

    private val storageRef = Firebase.storage.reference

    init {
        viewModelScope.launch {
            pet = petRepository.getById(1)

            val value = pet
            if (value != null) {
                setName(value.name)
                setBreed(value.breed)
                setGender(value.gender)
                setBirthDay(value.birthDay)
                setImage(value.image)
            }
            else {
                setBirthDay(SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date()))
            }
        }
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setBreed(breed: String) {
        _breed.value = breed
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setBirthDay(birthDay: String) {
        _birthDay.value = birthDay
    }

    fun setImage(image: String?) {
        _image.value = image
    }

    fun savePet() {
        if (checkFieldValidation()) {
            savePhoto()
            val newPet = PetEntity(pet?.id, name.value ?: "", birthDay.value ?: "",
                breed.value ?: "", gender.value ?: "", image.value)

            viewModelScope.launch {
                petRepository.save(newPet)
            }
        }
    }

    private fun checkFieldValidation(): Boolean {
        if (name.value.isNullOrBlank()) {
            _fieldError.value = "name"
            return false
        }
        else if (breed.value.isNullOrBlank()) {
            _fieldError.value = "breed"
            return false
        }
        else if (gender.value.isNullOrBlank()) {
            _fieldError.value = "gender"
            return false
        }
        else {
            _fieldError.value = ""
        }
        return true
    }

    private fun savePhoto() {
        if (image.value != pet?.image && image.value != null) {
            val fileName = UUID.randomUUID()
            val uploadTask = storageRef.child("$fileName").putFile(Uri.parse(image.value))

            uploadTask.addOnSuccessListener {
                storageRef.child("$fileName").downloadUrl.addOnSuccessListener {
                    _image.value = it.toString()
                    // здесь можно сделать лоадинг бар и когда все сохранится перейти на экран профиля либо показать что произошли какие то ошибки
                }.addOnFailureListener {
//                    binding?.let { Snackbar.make(it.root, getString(R.string.internet_connection_error), Snackbar.LENGTH_LONG).show() }
                }
            }.addOnFailureListener {
//                binding?.let { Snackbar.make(it.root, getString(R.string.internet_connection_error), Snackbar.LENGTH_LONG).show() }
            }
        }
    }

}
