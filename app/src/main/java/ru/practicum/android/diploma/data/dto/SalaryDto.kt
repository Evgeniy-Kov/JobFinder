package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param currency `Nullable` Код валюты
 * @param from `Nullable` Нижняя граница зарплаты
 * @param to `Nullable` Верхняя граница зарплаты
 * @param gross `Nullable` Признак, что границы зарплаты указаны до вычета налогов
 */

data class SalaryDto(
    @SerializedName("currency") val currency: String?,
    @SerializedName("from") val from: Int?,
    @SerializedName("to") val to: Int?,
    @SerializedName("gross") val gross: Boolean?
)
