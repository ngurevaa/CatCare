package ru.kpfu.itis.gureva.catcare.presentation.screens.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kpfu.itis.gureva.catcare.domain.usecase.GetWelcomeUseCase
import ru.kpfu.itis.gureva.catcare.presentation.mapper.WelcomeUIModelMapper
import ru.kpfu.itis.gureva.catcare.presentation.model.WelcomeUIModel
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val getWelcomeUseCase: GetWelcomeUseCase,
    private val mapper: WelcomeUIModelMapper
) : ViewModel() {
    private val _welcome = MutableLiveData<List<WelcomeUIModel>>()
    val welcome: LiveData<List<WelcomeUIModel>>
        get() = _welcome

    init {
        val list = getWelcomeUseCase.invoke()
        val newList = mutableListOf<WelcomeUIModel>()
        list.forEach {
            newList.add(mapper.mapWelcomeDomainModelToWelcomeUIModel(it))
        }
        _welcome.value = newList
    }
}
