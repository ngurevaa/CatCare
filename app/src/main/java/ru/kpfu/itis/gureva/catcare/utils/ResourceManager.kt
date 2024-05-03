package ru.kpfu.itis.gureva.catcare.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val context: Context) {
    fun getString(@StringRes res: Int): String = context.resources.getString(res)

    fun getString(@StringRes res: Int, vararg args: Any?): String {
        return context.resources.getString(res, *args)
    }
}
