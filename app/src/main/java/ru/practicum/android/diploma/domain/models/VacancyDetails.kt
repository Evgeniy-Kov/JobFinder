package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String?,
    val employerLogoUrl: String?,
    val employment: String?,
    val experience: String?,
    val salary: String,
    val contactsName: String?,
    val contactsEmail: String?,
    val contactsPhone: String?,
    val description: String,
    val keySkills: String,
    val professionalRoles: String,
    val schedule: String?,
    val workingDays: String?,
    val workingTimeIntervals: String?,
    val workingTimeModes: String?,
    val isFavourite: Boolean = false,
)
