package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacancySearchInteractor
import ru.practicum.android.diploma.domain.api.VacancySearchRepository
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancySearchInteractorImpl(
    private val repository: VacancySearchRepository
) : VacancySearchInteractor {
    override suspend fun searchVacancies(
        query: String,
        pageNumber: Int,
        options: Map<String, String>
    ): Resource<List<Vacancy>> {
        return repository.searchVacancies(query, pageNumber, options)
    }
}
