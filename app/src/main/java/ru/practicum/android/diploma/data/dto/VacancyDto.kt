package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param address `Nullable` Хранит экземпляр класса `AddressDto` с информацией об адресе
 * @param area Обязательное поле, хранит экземпляр класса `AreaDto` (Регион)
 * @param employer Хранит экземпляр класса `EmployerDto` с информацией о работодателе
 * @param id Обязательное поле, хранит идентификатор вакансии
 * @param name Обязательное поле, хранит название вакансии
 * @param salary `Nullable` Хранит экземпляр класса `SalaryDto` с информацией о зарплате
 */

data class VacancyDto(
    @SerializedName("address") val address: AddressDto?,
    @SerializedName("area") val area: AreaRegionDto,
    @SerializedName("employer") val employer: EmployerDto,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("salary") val salary: SalaryDto?,
)
