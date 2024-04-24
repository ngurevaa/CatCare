package ru.kpfu.itis.gureva.catcare.presentation.screens.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.PetRepository
import ru.kpfu.itis.gureva.catcare.utils.DownloadStatus
import ru.kpfu.itis.gureva.catcare.utils.FieldError
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.SavingStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class PetProfileEditingViewModel @AssistedInject constructor(
    private val petRepository: PetRepository,
    @Assisted("PET_ID") private val petId: Int?
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

    private val _fieldError = MutableLiveData<FieldError>()
    val fieldError: LiveData<FieldError>
        get() = _fieldError

    private val _downloadStatus = MutableLiveData<DownloadStatus>()
    val downloadStatus: LiveData<DownloadStatus>
        get() = _downloadStatus

    private val _savingStatus = MutableLiveData<SavingStatus>()
    val savingStatus: LiveData<SavingStatus>
        get() = _savingStatus

    private val storageRef = Firebase.storage.reference

    init {
        viewModelScope.launch {
            pet = petId?.let { petRepository.getById(it) }

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
                try {
                    petRepository.save(newPet)
                    _savingStatus.value = SavingStatus.OK
                } catch (ex: Exception) {
                    _savingStatus.value = SavingStatus.ERROR
                }
            }
        }
    }

    private fun checkFieldValidation(): Boolean {
        if (name.value.isNullOrBlank()) {
            _fieldError.value = FieldError.NAME
            return false
        }
        else if (breed.value.isNullOrBlank()) {
            _fieldError.value = FieldError.BREED
            return false
        }
        else if (gender.value.isNullOrBlank()) {
            _fieldError.value = FieldError.GENDER
            return false
        }
        else {
            _fieldError.value = FieldError.NONE
        }
        return true
    }

    private fun savePhoto() {
        if (image.value != pet?.image && image.value != null) {
            _downloadStatus.value = DownloadStatus.EXECUTION
            val fileName = UUID.randomUUID()
            val uploadTask = storageRef.child("$fileName").putFile(Uri.parse(image.value))

            uploadTask.addOnSuccessListener {
                storageRef.child("$fileName").downloadUrl.addOnSuccessListener {
                    _downloadStatus.value = DownloadStatus.OK
                    _image.value = it.toString()
                }.addOnFailureListener {
                    _downloadStatus.value = DownloadStatus.ERROR
                }
            }.addOnFailureListener {
                _downloadStatus.value = DownloadStatus.ERROR
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("PET_ID") petId: Int?): PetProfileEditingViewModel
    }
}
