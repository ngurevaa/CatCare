package ru.kpfu.itis.gureva.catcare.presentation.screens.diary.adding

import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.R
import ru.kpfu.itis.gureva.catcare.data.database.entity.DiaryEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.DiaryRepository
import ru.kpfu.itis.gureva.catcare.utils.DownloadStatus
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import ru.kpfu.itis.gureva.catcare.utils.ResourceManager
import ru.kpfu.itis.gureva.catcare.utils.SavingStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class DiaryAddingViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val resourceManager: ResourceManager
): ViewModel() {
    private var _image = MutableLiveData<String>()
    val image: LiveData<String>
        get() = _image

    private var _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    private val _downloadStatus = MutableLiveData<DownloadStatus>()
    val downloadStatus: LiveData<DownloadStatus>
        get() = _downloadStatus

    private val _savingStatus = MutableLiveData<SavingStatus>()
    val savingStatus: LiveData<SavingStatus>
        get() = _savingStatus

    private val storageRef = Firebase.storage.reference

    fun saveNote() {
        if (_description.value.isNullOrBlank()) {
            _error.value = resourceManager.getString(R.string.error_field_must_not_be_empty)
        }
        else {
            _error.value = null

            viewModelScope.launch {
                if (image.value != null) {
                    _downloadStatus.value = DownloadStatus.EXECUTION

                    val ref = storageRef.child("${UUID.randomUUID()}")
                    val uploadTask = ref.putFile(Uri.parse(_image.value))

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
                                diaryRepository.save(DiaryEntity(null, _description.value.toString(), downloadUri.toString(),
                                    SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date())))
                                _downloadStatus.value = DownloadStatus.OK
                                _savingStatus.value = SavingStatus.OK
                            }
                        } else {
                            _downloadStatus.value = DownloadStatus.ERROR
                        }
                    }

                    delay(15000)
                    if (_downloadStatus.value == DownloadStatus.EXECUTION) {
                        _downloadStatus.value = DownloadStatus.ERROR
                        uploadTask.cancel()
                    }
                }
                else {
                    _savingStatus.value = SavingStatus.OK
                    diaryRepository.save(DiaryEntity(null, _description.value.toString(), null,
                        SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date())))
                }
            }
        }
    }

    fun setImage(uri: String) {
        _image.value = uri
    }

    fun setDescription(description: String) {
        _description.value = description
    }
}
