package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param id Обязательное поле, хранит идентификатор отрасли
 * @param industries Хранит массив экземпляров класса `IndustryItemDto` с информацией о сферах деятельности
 * @param name Обязательное поле, хранит информацию о названии отрасли
 */

data class IndustryDto(
    @SerializedName("id") val id: String,
    @SerializedName("industries") val industries: List<IndustryItemDto>,
    @SerializedName("name") val name: String
)
