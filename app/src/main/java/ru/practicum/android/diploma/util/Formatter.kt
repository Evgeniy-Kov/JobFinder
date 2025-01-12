package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.AreaRegionDto

fun getFormattedSalary(salaryFrom: Int?, salaryTo: Int?, currency: String?, context: Context): String {
    return if (salaryFrom != null || salaryTo != null) {
        var result = ""
        if (salaryFrom != null) {
            result += String.format(context.getString(R.string.salary_from), salaryFrom)
        }
        if (salaryTo != null) {
            result += String.format(context.getString(R.string.salary_to), salaryTo)
        }
        if (currency != null) {
            result += currency
        }
        result
    } else {
        context.getString(R.string.salary_not_specified)
    }
}

fun getVacancyNameForViewHolder(vacancyName: String, city: String): String {
    return "$vacancyName, $city"
}

fun getVacancyAreaForDetails(areaDto: AreaRegionDto, addressDto: AddressDto?): String {
    return addressDto?.let { address ->
        listOfNotNull(
            address.city?.takeIf { it.isNotBlank() },
            address.street?.takeIf { it.isNotBlank() },
            address.building?.takeIf { it.isNotBlank() }
        ).joinToString(", ")
    } ?: areaDto.name
}
