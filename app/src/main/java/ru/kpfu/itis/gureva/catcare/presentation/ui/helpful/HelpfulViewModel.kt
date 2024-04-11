package ru.kpfu.itis.gureva.catcare.presentation.ui.helpful

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetCatFactUseCase
import ru.kpfu.itis.gureva.catcare.presentation.mapper.CatFactUIModelMapper
import ru.kpfu.itis.gureva.catcare.presentation.model.CatFactUIModel
import javax.inject.Inject

class HelpfulViewModel @Inject constructor(
    private val getCatFactUseCase: GetCatFactUseCase,
    private val mapper: CatFactUIModelMapper
) : ViewModel() {

    private val _currentCatFactFlow = MutableStateFlow<CatFactUIModel?>(null)
    val currentCatFactFlow: StateFlow<CatFactUIModel?>
        get() = _currentCatFactFlow

    private val _errorFlow = MutableStateFlow<Throwable?>(null)
    val errorFlow: StateFlow<Throwable?>
        get() = _errorFlow

    fun getFact() {
        viewModelScope.launch {
            runCatching {
                mapper.mapDomainModelToUIModel(getCatFactUseCase.invoke())
            }.onSuccess {
                _currentCatFactFlow.value = it
            }.onFailure {
                _errorFlow.value = it
            }
        }
    }
}
