package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param abbr Хранит символ валюты
 * @param code Хранит код валюты
 * @param name Хранит название валюты
 * @param rate Хранит курс по отношению к рублю
 */

data class CurrencyDto(
    @SerializedName("abbr") val abbr: String,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("rate") val rate: Double
)
