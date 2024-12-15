package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.data.dto.VacancyDto
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
        salaryTo = salary?.to
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
        contactsPhone = contacts?.phones?.first()?.formatted,
        description = description,
        professionalRoles = professionalRoles.joinToString("\n") { it.name },
        keySkills = keySkills.joinToString("\n") { it.name },
        schedule = schedule?.name,
        workingDays = workingDays?.joinToString("\n") { it.name },
        workingTimeIntervals = workingTimeIntervals?.joinToString("\n") { it.name },
        workingTimeModes = workingTimeModes?.joinToString("\n") { it.name }
    )
}

fun FavouriteVacancyEntity.toVacancyDetails(): VacancyDetails {
    return VacancyDetails(
        id = vacancyId,
        name = name,
        city = city,
        employerName = employerName,
        employerLogoUrl = employerLogoUrl,
        employment = employment,
        experience = experience,
        salaryFrom = salaryFrom,
        salaryTo = salaryTo,
        contactsName = contactsName,
        contactsEmail = contactsEmail,
        contactsPhone = contactsPhone,
        description = description,
        keySkills = keySkills,
        professionalRoles = professionalRoles,
        schedule = schedule,
        workingDays = workingDays,
        workingTimeIntervals = workingTimeIntervals,
        workingTimeModes = workingTimeModes,
        isFavourite = true
    )
}

fun VacancyDetails.toFavouriteVacancyEntity(): FavouriteVacancyEntity {
    return FavouriteVacancyEntity(
        vacancyId = id,
        name = name,
        city = city,
        employerName = employerName,
        employerLogoUrl = employerLogoUrl,
        employment = employment,
        experience = experience,
        salaryFrom = salaryFrom,
        salaryTo = salaryTo,
        contactsName = contactsName,
        contactsEmail = contactsEmail,
        contactsPhone = contactsPhone,
        description = description,
        keySkills = keySkills,
        professionalRoles = professionalRoles,
        schedule = schedule,
        workingDays = workingDays,
        workingTimeIntervals = workingTimeIntervals,
        workingTimeModes = workingTimeModes,
    )
}
