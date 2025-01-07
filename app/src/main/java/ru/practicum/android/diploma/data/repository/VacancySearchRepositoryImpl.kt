package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.CurrencyDto
import ru.practicum.android.diploma.data.network.DictionariesRequest
import ru.practicum.android.diploma.data.network.DictionariesResponse
import ru.practicum.android.diploma.data.network.VacanciesSearchRequest
import ru.practicum.android.diploma.data.network.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.api.VacancySearchRepository
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.toVacancy

class VacancySearchRepositoryImpl(
    private val networkClient: NetworkClient,
) : VacancySearchRepository {

    private var currencies: List<CurrencyDto>? = null

    override suspend fun searchVacancies(
        query: String,
        pageNumber: Int,
        options: Map<String, String>
    ): Resource<List<Vacancy>> {
        if (currencies == null) {
            currencies = getCurrencyList()
        }

        val request = VacanciesSearchRequest(query, pageNumber, options)
        val response = networkClient.doRequest(request)

        return when (response) {
            is VacanciesSearchResponse -> {
                val vacancies = response.items.map { dto ->
                    dto.toVacancy().copy(
                        currencySymbol = currencies?.find { it.code == dto.salary?.currency }?.abbr
                    )
                }
                Resource.Success(vacancies)
            }

            else -> {
                Resource.Error(response.resultCode)
            }
        }
    }

    private suspend fun getCurrencyList(): List<CurrencyDto> {
        val response = networkClient.doRequest(DictionariesRequest())
        val currencyList = mutableListOf<CurrencyDto>()
        if (response.resultCode == NetworkClient.SUCCESS) {
            currencyList.addAll((response as DictionariesResponse).dictionary.currency)
        }
        return currencyList
    }
}
