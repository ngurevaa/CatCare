package ru.kpfu.itis.gureva.catcare.presentation.screens.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.data.database.entity.DiaryEntity
import ru.kpfu.itis.gureva.catcare.data.database.entity.PetEntity
import ru.kpfu.itis.gureva.catcare.data.database.repository.DiaryRepository
import ru.kpfu.itis.gureva.catcare.presentation.screens.base.BaseViewModel
import ru.kpfu.itis.gureva.catcare.utils.Formatter
import java.text.SimpleDateFormat
import javax.inject.Inject

class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : BaseViewModel() {
    val notes: LiveData<List<DiaryEntity>> = diaryRepository.getAll().map {
        val dateFormat = SimpleDateFormat(Formatter.DATE_WITHOUT_TIME)
        it.sortedByDescending { item ->
            dateFormat.parse(item.date)?.time
        }
    }

    private var removedItem: DiaryEntity? = null

    override fun removeItem(position: Int) {
        viewModelScope.launch {
            removedItem = notes.value?.get(position)
            removedItem?.let { diaryRepository.delete(it) }
        }
    }

    override fun returnItem() {
        viewModelScope.launch {
            removedItem?.let {
                diaryRepository.save(it)
            }
        }
    }
}
