package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param size90 `Nullable` хранит ссылку на логотип компании
 * @param size240 `Nullable` хранит ссылку на логотип компании
 * @param original `Nullable` хранит ссылку на логотип компании
 */

data class LogoUrlsDto(
    @SerializedName("90") val size90: String?,
    @SerializedName("240") val size240: String?,
    @SerializedName("original") val original: String?
)
