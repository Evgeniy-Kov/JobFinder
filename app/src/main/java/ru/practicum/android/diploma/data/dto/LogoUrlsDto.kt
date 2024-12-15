package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class LogoUrlsDto(
    @SerializedName("90")val size90: String?,
    @SerializedName("240")val size240: String?,
    @SerializedName("original")val original: String?
)
