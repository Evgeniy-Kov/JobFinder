package ru.practicum.android.diploma.util

import com.google.gson.Gson
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
        professionalRoles = professionalRoles.map { it.name },
        keySkills = keySkills.map { it.name },
        schedule = schedule?.name,
        workingDays = workingDays?.map { it.name },
        workingTimeIntervals = workingTimeIntervals?.map { it.name },
        workingTimeModes = workingTimeModes?.map { it.name }
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
        keySkills = Gson().fromJson(keySkills, Array<String>::class.java).toList(),
        professionalRoles = Gson().fromJson(professionalRoles, Array<String>::class.java).toList(),
        schedule = schedule,
        workingDays = Gson().fromJson(workingDays, Array<String>::class.java).toList(),
        workingTimeIntervals = Gson().fromJson(workingTimeIntervals, Array<String>::class.java).toList(),
        workingTimeModes = Gson().fromJson(workingTimeModes, Array<String>::class.java).toList(),
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
        keySkills = Gson().toJson(keySkills),
        professionalRoles = Gson().toJson(professionalRoles),
        schedule = schedule,
        workingDays = Gson().toJson(workingDays),
        workingTimeIntervals = Gson().toJson(workingTimeIntervals),
        workingTimeModes = Gson().toJson(workingTimeModes),
    )
}
