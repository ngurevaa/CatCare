package ru.kpfu.itis.gureva.catcare.utils

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gureva.catcare.presentation.di.AssistedViewModelFactory

inline fun <T> Flow<T>.observe(fragment: Fragment, crossinline block: (T) -> Unit): Job {
    val lifecycleOwner = fragment.viewLifecycleOwner
    return lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            collect { data -> block(data) }
        }
    }
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: (stateHandle: SavedStateHandle) -> T
): Lazy<T> {
    return viewModels<T> {
        AssistedViewModelFactory(this, create) }
}

fun Snackbar.setMaxLines(lines: Int): Snackbar = apply {
    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = lines
}
