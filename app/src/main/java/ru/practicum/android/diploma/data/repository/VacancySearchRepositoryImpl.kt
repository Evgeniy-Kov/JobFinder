package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.network.VacanciesSearchRequest
import ru.practicum.android.diploma.data.network.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.api.VacancySearchRepository
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.toVacancy

class VacancySearchRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
) : VacancySearchRepository {
    override suspend fun searchVacancies(
        query: String,
        pageNumber: Int,
        options: Map<String, String>
    ): Resource<List<Vacancy>> {
        val request = VacanciesSearchRequest(query, pageNumber, options)
        val response = networkClient.doRequest(request)

        return when (response) {
            is VacanciesSearchResponse -> {
                val vacancies = response.items.map { it.toVacancy() }
                Resource.Success(vacancies)
            }

            else -> {
                Resource.Error(response.resultCode)
            }
        }
    }
}
