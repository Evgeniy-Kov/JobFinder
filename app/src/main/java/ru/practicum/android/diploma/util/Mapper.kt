package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

fun VacancyDto.toVacancy(context: Context): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        city = area.name,
        employerName = employer.name,
        employerLogoUrl = employer.logoUrls?.original,
        salary = getFormattedSalary(salary, context)
    )
}
