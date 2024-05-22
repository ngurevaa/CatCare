package ru.kpfu.itis.gureva.catcare.presentation.screens.diary.adding

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.storage.UploadTask
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

data class DiaryState(
    val image: String? = null,
    val description: String = "",
    val error: String? = null
)

class DiaryAddingViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val resourceManager: ResourceManager
): ViewModel() {
    private val _diaryState = MutableLiveData<DiaryState>()
    val diaryState: LiveData<DiaryState>
        get() = _diaryState

    private val _downloadStatus = MutableLiveData<DownloadStatus>()
    val downloadStatus: LiveData<DownloadStatus>
        get() = _downloadStatus

    private val _savingStatus = MutableLiveData<SavingStatus>()
    val savingStatus: LiveData<SavingStatus>
        get() = _savingStatus

    private val storageRef = Firebase.storage.reference

    init {
        _diaryState.value = DiaryState()
    }

    fun saveNote() {
        if (_diaryState.value?.description.isNullOrBlank()) {
            _diaryState.value = _diaryState.value?.copy(error = resourceManager.getString(R.string.error_field_must_not_be_empty))
        }
        else {
            _diaryState.value = _diaryState.value?.copy(error = null)

            viewModelScope.launch {
                if (_diaryState.value?.image != null) {
                    _downloadStatus.value = DownloadStatus.EXECUTION
                    doTask()

                    delay(15000)
                    if (_downloadStatus.value == DownloadStatus.EXECUTION) {
                        _downloadStatus.value = DownloadStatus.LONG_EXECUTION
                    }
                }
                else {
                    _savingStatus.value = SavingStatus.OK
                    saveDiary(null)
                }
            }
        }
    }

    private suspend fun doTask() {
        val ref = storageRef.child("${UUID.randomUUID()}")
        val uploadTask = ref.putFile(Uri.parse(_diaryState.value?.image.toString()))

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
                    _downloadStatus.value = DownloadStatus.OK
                    saveDiary(downloadUri.toString())
                    _savingStatus.value = SavingStatus.OK
                }
            } else {
                _downloadStatus.value = DownloadStatus.ERROR
            }
        }
    }

    private suspend fun saveDiary(image: String?) {
        diaryRepository.save(DiaryEntity(null, _diaryState.value?.description.toString(), image,
            SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).format(Date())))
    }

    fun setImage(uri: String) {
        _diaryState.value = _diaryState.value?.copy(image = uri)
    }

    fun setDescription(desc: String) {
        _diaryState.value = _diaryState.value?.copy(description = desc)
    }
}
