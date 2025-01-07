package ru.practicum.android.diploma.data.dto

/**
 * @param city Обязательное поле, хранит код города
 * @param comment `Nullable` хранит комментарий с удобным временем для звонка по этому номеру
 * @param country Обязательное поле, хранит код страны
 * @param formatted Обязательное поле, хранит номер телефона в отформатированном виде
 * @param number Обязательное поле, хранит номер телефона
 */

data class PhoneDto(
    val city: String,
    val comment: String?,
    val country: String,
    val formatted: String,
    val number: String
)
