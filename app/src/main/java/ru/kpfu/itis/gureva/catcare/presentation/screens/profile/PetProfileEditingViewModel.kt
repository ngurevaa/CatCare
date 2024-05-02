package ru.kpfu.itis.gureva.catcare.presentation.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.UploadTask
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

data class PetState(
    val name: String = "",
    val breed: String = "",
    val gender: String = "",
    val birthDay: String = "",
    val image: String? = null
)

class PetProfileEditingViewModel @AssistedInject constructor(
    private val petRepository: PetRepository,
    @Assisted("PET_ID") private var petId: Int?
) : ViewModel() {
    private val _petState = MutableLiveData<PetState>()
    val petState: LiveData<PetState>
        get() = _petState

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
            _petState.value = PetState()

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
        _petState.value = _petState.value?.copy(name = name)
    }

    fun setBreed(breed: String) {
        _petState.value = _petState.value?.copy(breed = breed)
    }

    fun setGender(gender: String) {
        _petState.value = _petState.value?.copy(gender = gender)
    }

    fun setBirthDay(birthDay: String) {
        _petState.value = _petState.value?.copy(birthDay = birthDay)
    }

    fun setImage(image: String?) {
        _petState.value = _petState.value?.copy(image = image)
    }

    fun savePet() {
        if (checkFieldValidation()) {
            val newPet = PetEntity(pet?.id, _petState.value?.name ?: "", _petState.value?.birthDay ?: "",
                _petState.value?.breed ?: "", _petState.value?.gender ?: "", _petState.value?.image)

            viewModelScope.launch {
                try {
                    petId = petRepository.save(newPet).toInt()
                    savePhoto(_petState.value?.image != pet?.image)
                } catch (ex: Exception) {
                    _savingStatus.value = SavingStatus.ERROR
                }
            }
        }
    }

    private fun checkFieldValidation(): Boolean {
        if (_petState.value?.name.isNullOrBlank()) {
            _fieldError.value = FieldError.NAME
            return false
        }
        else if (_petState.value?.breed.isNullOrBlank()) {
            _fieldError.value = FieldError.BREED
            return false
        }
        else if (_petState.value?.gender.isNullOrBlank()) {
            _fieldError.value = FieldError.GENDER
            return false
        }
        else {
            _fieldError.value = FieldError.NONE
        }
        return true
    }

    private fun savePhoto(flag: Boolean) {
        if (flag && _petState.value?.image != null) {
            _downloadStatus.value = DownloadStatus.EXECUTION

            val ref = storageRef.child("${UUID.randomUUID()}")
            val uploadTask = ref.putFile(Uri.parse(_petState.value?.image))

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        _downloadStatus.value = DownloadStatus.ERROR
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result

                    viewModelScope.launch {
                        petRepository.update(petId ?: 1, downloadUri.toString())
                        _downloadStatus.value = DownloadStatus.OK
                        _savingStatus.value = SavingStatus.OK
                    }
                } else {
                    _downloadStatus.value = DownloadStatus.ERROR
                }
            }
        }
        else {
            _savingStatus.value = SavingStatus.OK
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("PET_ID") petId: Int?): PetProfileEditingViewModel
    }
}
