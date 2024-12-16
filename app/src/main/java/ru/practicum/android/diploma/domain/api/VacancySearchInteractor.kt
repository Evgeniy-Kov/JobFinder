package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancySearchInteractor {
    suspend fun searchVacancies(
        query: String,
        pageNumber: Int,
        options: Map<String, String>
    ): Resource<List<Vacancy>>
}
