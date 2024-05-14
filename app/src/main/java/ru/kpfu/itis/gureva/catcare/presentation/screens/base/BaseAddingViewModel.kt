package ru.kpfu.itis.gureva.catcare.presentation.screens.base

import androidx.lifecycle.ViewModel

abstract class BaseAddingViewModel : ViewModel() {
    abstract fun save(description: String, date: String)
}
