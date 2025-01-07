package ru.practicum.android.diploma.data.dto

/**
 * @param address `Nullable` Хранит экземпляр класса `AddressDto` с информацией об адресе
 * @param area Обязательное поле, хранит экземпляр класса `AreaDto` (Регион)
 * @param employer Хранит экземпляр класса `EmployerDto` с информацией о работодателе
 * @param id Обязательное поле, хранит идентификатор вакансии
 * @param name Обязательное поле, хранит название вакансии
 * @param salary `Nullable` Хранит экземпляр класса `SalaryDto` с информацией о зарплате
 */

data class VacancyDto(
    val address: AddressDto?,
    val area: AreaRegionDto,
    val employer: EmployerDto,
    val id: String,
    val name: String,
    val salary: SalaryDto?,
)
