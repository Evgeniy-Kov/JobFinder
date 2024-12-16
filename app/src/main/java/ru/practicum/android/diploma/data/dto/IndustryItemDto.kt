package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param id Обязательное поле, хранит идентификатор сферы деятельности
 * @param name Обязательное поле, хранит информацию о названии сферы деятельности
 */

data class IndustryItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
