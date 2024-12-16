package ru.practicum.android.diploma.domain.models

/**
 * @param id Хранит идентификатор вакансии
 * @param name Хранит название вакансии
 * @param city Хранит название региона
 * @param employerName Хранит название компании
 * @param employerLogoUrl `Nullable` Хранит ссылку на логотип компании
 * @param salaryFrom `Nullable` Хранит информацию о нижней границе зарплаты
 * @param salaryTo `Nullable` Хранит информацию о верхней границе зарплаты
 */

data class Vacancy(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String,
    val employerLogoUrl: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
)
