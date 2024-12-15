package ru.practicum.android.diploma.domain.models

/**
 * @param id Хранит идентификатор вакансии
 * @param name Хранит название вакансии
 * @param city Хранит название региона
 * @param employerName Хранит название компании
 * @param employerLogoUrl `Nullable` Хранит ссылку на логотип компании
 * @param salary Хранит информацию о зарплате в отформатированном виде
 */

data class Vacancy(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String,
    val employerLogoUrl: String?,
    val salary: String,
)
