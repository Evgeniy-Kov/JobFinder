package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param id Обязательное поле, хранит идентификатор региона
 * @param name Обязательное поле, хранит информацию о названии региона
 * @param url Обязательное поле, хранит ссылку на информацию о регионе
 */

data class AreaRegionDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
