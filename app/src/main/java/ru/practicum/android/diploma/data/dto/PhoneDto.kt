package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param city Обязательное поле, хранит код города
 * @param comment `Nullable` хранит комментарий с удобным временем для звонка по этому номеру
 * @param country Обязательное поле, хранит код страны
 * @param formatted Обязательное поле, хранит номер телефона в отформатированном виде
 * @param number Обязательное поле, хранит номер телефона
 */

data class PhoneDto(
    @SerializedName("city") val city: String,
    @SerializedName("comment") val comment: String?,
    @SerializedName("country") val country: String,
    @SerializedName("formatted") val formatted: String,
    @SerializedName("number") val number: String
)
