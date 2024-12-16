package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface VacancyDetailsInteractor {
    suspend fun getVacancyDetails(vacancyId: String): Resource<VacancyDetails>
}
