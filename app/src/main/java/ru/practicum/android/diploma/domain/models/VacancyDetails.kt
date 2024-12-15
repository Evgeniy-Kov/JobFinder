package ru.practicum.android.diploma.domain.models

/**
 * @param id Хранит идентификатор вакансии
 * @param name Хранит название вакансии
 * @param city Хранит название региона
 * @param employerName `Nullable` Хранит название компании
 * @param employerLogoUrl `Nullable` Хранит ссылку на логотип компании
 * @param employment `Nullable` Хранит информацию о типе занятости
 * @param experience `Nullable` Хранит информацию об опыте работы
 * @param salary Хранит информацию о зарплате в отформатированном виде
 * @param contactsName  `Nullable` Хранит информацию об имени контакта
 * @param contactsEmail  `Nullable` Хранит Email для связи
 * @param contactsPhone  `Nullable` Хранит номер телефона для связи в отформатированном виде
 * @param description Хранит описание вакансии в html, не менее 200 символов
 * @param keySkills Хранит информацию о ключевых навыках, не более 30
 * @param professionalRoles Хранит информацию о профессиональных ролях
 * @param schedule `Nullable` Хранит информацию о графике работы
 * @param workingDays `Nullable` Хранит информацию о рабочих днях
 * @param workingTimeIntervals `Nullable` Хранит информацию о временных интервалах работы
 * @param workingTimeModes `Nullable` Хранит информацию о режимах работы
 * @param isFavourite Хранит информацию о добавлении вакансии в список избранных
 */

data class VacancyDetails(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String?,
    val employerLogoUrl: String?,
    val employment: String?,
    val experience: String?,
    val salary: String,
    val contactsName: String?,
    val contactsEmail: String?,
    val contactsPhone: String?,
    val description: String,
    val keySkills: String,
    val professionalRoles: String,
    val schedule: String?,
    val workingDays: String?,
    val workingTimeIntervals: String?,
    val workingTimeModes: String?,
    val isFavourite: Boolean = false,
)
