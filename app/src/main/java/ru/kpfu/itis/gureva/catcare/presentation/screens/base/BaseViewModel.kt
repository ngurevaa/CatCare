package ru.kpfu.itis.gureva.catcare.presentation.screens.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    abstract fun removeItem(position: Int)

    abstract fun returnItem()
}
