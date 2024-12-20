package ru.practicum.android.diploma.domain.models

/**
 * @param id Хранит идентификатор вакансии
 * @param name Хранит название вакансии
 * @param city Хранит название региона
 * @param employerName `Nullable` Хранит название компании
 * @param employerLogoUrl `Nullable` Хранит ссылку на логотип компании
 * @param employment `Nullable` Хранит информацию о типе занятости
 * @param experience `Nullable` Хранит информацию об опыте работы
 * @param salaryFrom `Nullable` Хранит информацию о нижней границе зарплаты
 * @param salaryTo `Nullable` Хранит информацию о верхней границе зарплаты
 * @param contactsName  `Nullable` Хранит информацию об имени контакта
 * @param contactsEmail  `Nullable` Хранит Email для связи
 * @param contactsPhone  `Nullable` Хранит номер телефона для связи в отформатированном виде
 * @param description Хранит описание вакансии в html, не менее 200 символов
 * @param keySkills Хранит информацию о ключевых навыках, не более 30 в виде списка
 * @param professionalRoles Хранит информацию о профессиональных ролях в виде списка
 * @param schedule `Nullable` Хранит информацию о графике работы
 * @param workingDays `Nullable` Хранит информацию о рабочих днях в виде списка
 * @param workingTimeIntervals `Nullable` Хранит информацию о временных интервалах работы в виде списка
 * @param workingTimeModes `Nullable` Хранит информацию о режимах работы в виде списка
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
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val contactsName: String?,
    val contactsEmail: String?,
    val contactsPhone: String?,
    val description: String,
    val keySkills: List<String>?,
    val professionalRoles: List<String>,
    val schedule: String?,
    val workingDays: List<String>?,
    val workingTimeIntervals: List<String>?,
    val workingTimeModes: List<String>?,
    val url: String,
    val isFavourite: Boolean = false,
)
