package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param vacancyId Хранит идентификатор вакансии
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
 * @param keySkills Хранит информацию о ключевых навыках, не более 30 в JSON формате
 * @param professionalRoles Хранит информацию о профессиональных ролях в JSON формате
 * @param schedule `Nullable` Хранит информацию о графике работы
 * @param workingDays `Nullable` Хранит информацию о рабочих днях в JSON формате
 * @param workingTimeIntervals `Nullable` Хранит информацию о временных интервалах работы в JSON формате
 * @param workingTimeModes `Nullable` Хранит информацию о режимах работы в JSON формате
 */

@Entity(tableName = "favorite_table")
data class FavouriteVacancyEntity(
    @PrimaryKey
    val vacancyId: String,
    val name: String,
    val city: String,
    val employerName: String?,
    val employerLogoUrl: String?,
    val employment: String?,
    val experience: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currencySymbol: String?,
    val contactsName: String?,
    val contactsEmail: String?,
    val contactsPhone: String?,
    val description: String,
    val keySkills: String?,
    val professionalRoles: String,
    val schedule: String?,
    val workingDays: String?,
    val workingTimeIntervals: String?,
    val workingTimeModes: String?,
    val url: String,
)
