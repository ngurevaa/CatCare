package ru.kpfu.itis.gureva.catcare.presentation.ui.helpful

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetCatFactUseCase
import ru.kpfu.itis.gureva.catcare.presentation.model.CatFactUIModel
import javax.inject.Inject

class HelpfulViewModel @Inject constructor(
    private val getCatFactUseCase: GetCatFactUseCase
) : ViewModel() {

    private val _currentCatFactFlow = MutableStateFlow<CatFactUIModel?>(null)
    val currentCatFactFlow: StateFlow<CatFactUIModel?>
        get() = _currentCatFactFlow

    fun getFact() {
        viewModelScope.launch {
            runCatching {
                getCatFactUseCase.invoke()
            }.onSuccess {
                _currentCatFactFlow.value = it
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
