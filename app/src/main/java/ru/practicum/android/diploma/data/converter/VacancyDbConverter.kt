package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.convertFromJson
import ru.practicum.android.diploma.util.convertToJson

class VacancyDbConverter {
    fun convert(vacancy: VacancyDetails): FavouriteVacancyEntity {
        return FavouriteVacancyEntity(
            vacancyId = vacancy.id,
            name = vacancy.name,
            city = vacancy.city,
            employerName = vacancy.employerName,
            employerLogoUrl = vacancy.employerLogoUrl,
            employment = vacancy.employment,
            experience = vacancy.experience,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            contactsName = vacancy.contactsName,
            contactsEmail = vacancy.contactsEmail,
            contactsPhone = vacancy.contactsPhone,
            description = vacancy.description,
            keySkills = convertToJson(vacancy.keySkills),
            professionalRoles = convertToJson(vacancy.professionalRoles),
            schedule = vacancy.schedule,
            workingDays = vacancy.workingDays?.let { convertToJson(it) },
            workingTimeIntervals = vacancy.workingTimeIntervals?.let { convertToJson(it) },
            workingTimeModes = vacancy.workingTimeModes?.let { convertToJson(it) }
        )
    }

    fun convert(vacancy: FavouriteVacancyEntity): VacancyDetails {
        return VacancyDetails(
            id = vacancy.vacancyId,
            name = vacancy.name,
            city = vacancy.city,
            employerName = vacancy.employerName,
            employerLogoUrl = vacancy.employerLogoUrl,
            employment = vacancy.employment,
            experience = vacancy.experience,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            contactsName = vacancy.contactsName,
            contactsEmail = vacancy.contactsEmail,
            contactsPhone = vacancy.contactsPhone,
            description = vacancy.description,
            keySkills = convertFromJson(vacancy.keySkills),
            professionalRoles = convertFromJson(vacancy.professionalRoles),
            schedule = vacancy.schedule,
            workingDays = vacancy.workingDays?.let { convertFromJson(it) },
            workingTimeIntervals = vacancy.workingTimeIntervals?.let { convertFromJson(it) },
            workingTimeModes = vacancy.workingTimeModes?.let { convertFromJson(it) },
            isFavourite = true,
        )
    }
}
