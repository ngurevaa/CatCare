package ru.kpfu.itis.gureva.catcare.presentation.screens.helpful

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetNewsUseCase
import ru.kpfu.itis.gureva.catcare.presentation.mapper.CatFactUIModelMapper
import ru.kpfu.itis.gureva.catcare.presentation.mapper.NewsUIModelMapper
import ru.kpfu.itis.gureva.catcare.presentation.model.NewsUIModel
import javax.inject.Inject

class HelpfulViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val mapper: NewsUIModelMapper
): ViewModel() {

    private val _news = MutableLiveData<List<NewsUIModel>>()
    val news: LiveData<List<NewsUIModel>>
        get() = _news

    init {
        viewModelScope.launch {
            val list = getNewsUseCase.invoke()
            val newList = mutableListOf<NewsUIModel>()
            list.forEach {
                newList.add(mapper.mapDomainModelToUIModel(it))
            }
            _news.value = newList
        }
    }
}
