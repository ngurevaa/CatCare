package ru.kpfu.itis.gureva.catcare.presentation.ui.helpful

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.di.ServiceLocator
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetCatFactUseCase
import ru.kpfu.itis.gureva.catcare.presentation.model.CatFactUIModel

class HelpfulViewModel(
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val useCase = ServiceLocator.getCatFactUseCase

                return HelpfulViewModel(
                    getCatFactUseCase = useCase
                ) as T
            }
        }
    }
}
