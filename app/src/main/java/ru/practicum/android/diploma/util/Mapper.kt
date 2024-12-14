package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

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

fun VacancyDetailsDto.toVacancyDetails(context: Context): VacancyDetails {
    return VacancyDetails(
        id = id,
        name = name,
        city = area.name,
        employerName = employer?.name,
        employerLogoUrl = employer?.logoUrls?.original,
        salary = getFormattedSalary(salary, context),
        employment = employment?.name,
        experience = experience?.name,
        contactsName = contacts?.name,
        contactsEmail = contacts?.email,
        contactsPhone = contacts?.phones?.first()?.formatted,
        description = description,
        professionalRoles = professionalRoles.joinToString("\n"){it.name},
        keySkills = keySkills.joinToString("\n"){it.name},
        schedule = schedule?.name,
        workingDays = workingDays?.joinToString("\n"){it.name},
        workingTimeIntervals = workingTimeIntervals?.joinToString("\n"){it.name},
        workingTimeModes = workingTimeModes?.joinToString("\n"){it.name}
    )
}
