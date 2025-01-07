package ru.practicum.android.diploma.domain.models

/**
 * @param country `Nullable` Хранит экземпляр класса `Country` с информацией о стране
 * @param region `Nullable` Хранит экземпляр класса `Region` с информацией о регионе
 * @param industry `Nullable` Хранит экземпляр класса `Industry` с информацией о регионе
 * @param salary `Nullable` Хранит информацию об уровне зарплаты
 * @param onlyWithSalary  Хранит флаг, указывающий на необходимость игнорирования вакансий без указания зарплаты
 * @property isDefault  Хранит флаг, указывающий на то, являются ли значения фильтра значениями по умолчанию
 */

data class Filter(
    val country: Country? = null,
    val region: Region? = null,
    val industry: Industry? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
) {
    val isDefault: Boolean
        get() = country == null && region == null && industry == null && !onlyWithSalary
}
