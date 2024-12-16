package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param address `Nullable` Хранит экземпляр класса `AddressDto` с информацией об адресе
 * @param area Обязательное поле, хранит экземпляр класса `AreaDto` (Регион)
 * @param contacts  `Nullable` Хранит экземпляр класса `ContactsDto` Контактная информация
 * @param description Обязательное поле, хранит описание вакансии в html, не менее 200 символов
 * @param employer `Nullable` Хранит экземпляр класса `EmployerDto` с информацией о работодателе
 * @param employment `Nullable` Хранит экземпляр класса `EmploymentDto` с информацией о типе занятости
 * @param experience `Nullable` Хранит экземпляр класса `ExperienceDto` с информацией об опыте работы
 * @param id Обязательное поле, хранит идентификатор вакансии
 * @param keySkills Обязательное поле, хранит массив экземпляров класса `EmploymentDto` с информацией о ключевых навыках, не более 30
 * @param name Обязательное поле, хранит название вакансии
 * @param professionalRoles Обязательное поле, хранит массив экземпляров класса `ProfessionalRoleDto` с информацией профессиональных ролях
 * @param salary `Nullable` Хранит экземпляр класса `SalaryDto` с информацией о зарплате
 * @param schedule `Nullable` Хранит экземпляр класса `ScheduleDto` с информацией о графике работы
 * @param workingDays `Nullable` Хранит массив экземпляров класса `WorkingDaysDto` с информацией о рабочих днях
 * @param workingTimeIntervals `Nullable` Хранит массив экземпляров класса `WorkingTimeIntervalsDto` с информацией о временных интервалах работы
 * @param workingTimeModes `Nullable` Хранит массив экземпляров класса `WorkingTimeModesDto` с информацией о режимах работы
 */

data class VacancyDetailsDto(
    @SerializedName("address") val address: AddressDto?,
    @SerializedName("area") val area: AreaRegionDto,
    @SerializedName("contacts") val contacts: ContactsDto?,
    @SerializedName("description") val description: String,
    @SerializedName("employer") val employer: EmployerDto?,
    @SerializedName("employment") val employment: EmploymentDto?,
    @SerializedName("experience") val experience: ExperienceDto?,
    @SerializedName("id") val id: String,
    @SerializedName("key_skills") val keySkills: List<KeySkillDto>,
    @SerializedName("name") val name: String,
    @SerializedName("professional_roles") val professionalRoles: List<ProfessionalRoleDto>,
    @SerializedName("salary") val salary: SalaryDto?,
    @SerializedName("schedule") val schedule: ScheduleDto?,
    @SerializedName("working_days") val workingDays: List<WorkingDaysDto>?,
    @SerializedName("working_time_intervals") val workingTimeIntervals: List<WorkingTimeIntervalsDto>?,
    @SerializedName("working_time_modes") val workingTimeModes: List<WorkingTimeModesDto>?
)
