package ru.kpfu.itis.gureva.catcare.data.remote.response

import com.google.gson.annotations.SerializedName

data class CatFactResponse(
    @SerializedName("data")
    val data: List<String>
)
