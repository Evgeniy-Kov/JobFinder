package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param currency Хранит массив экземпляров класса `CurrencyDto` с информацией о валютах
 */

data class DictionaryDto(
    @SerializedName("currency") val currency: List<CurrencyDto>
)
