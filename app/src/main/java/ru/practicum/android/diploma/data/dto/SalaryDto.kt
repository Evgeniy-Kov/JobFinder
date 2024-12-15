package ru.practicum.android.diploma.data.dto

/**
 * @param currency `Nullable` Код валюты
 * @param from `Nullable` Нижняя граница зарплаты
 * @param to `Nullable` Верхняя граница зарплаты
 * @param gross `Nullable` Признак, что границы зарплаты указаны до вычета налогов
 */

data class SalaryDto(
    val currency: String?,
    val from: Int?,
    val to: Int?,
    val gross: Boolean?
)
