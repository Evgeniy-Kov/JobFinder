package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.IndustryItemDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

fun VacancyDto.toVacancy(): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        city = area.name,
        employerName = employer.name,
        employerLogoUrl = employer.logoUrls?.original,
        salaryFrom = salary?.from,
        salaryTo = salary?.to,
    )
}

fun VacancyDetailsDto.toVacancyDetails(): VacancyDetails {
    return VacancyDetails(
        id = id,
        name = name,
        city = area.name,
        employerName = employer?.name,
        employerLogoUrl = employer?.logoUrls?.original,
        salaryFrom = salary?.from,
        salaryTo = salary?.to,
        employment = employment?.name,
        experience = experience?.name,
        contactsName = contacts?.name,
        contactsEmail = contacts?.email,
        contactsPhone = contacts?.phones?.firstOrNull()?.formatted,
        description = description,
        professionalRoles = professionalRoles.map { it.name },
        keySkills = keySkills.map { it.name },
        schedule = schedule?.name,
        workingDays = workingDays?.map { it.name },
        workingTimeIntervals = workingTimeIntervals?.map { it.name },
        workingTimeModes = workingTimeModes?.map { it.name },
        url = url
    )
}

fun AreaDto.toArea(): Area {
    return Area(
        parentId = parentId,
        id = id,
        name = name,
        areas = areas.map { it.toArea() }
    )
}

fun IndustryDto.toIndustry(): Industry {
    return Industry(
        id = id,
        name = name,
        industry = industries.map { it.toIndustry() }
    )
}

fun IndustryItemDto.toIndustry(): Industry {
    return Industry(
        id = id,
        name = name,
        industry = null
    )
}
