package ru.practicum.android.diploma.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.CurrencyDto
import ru.practicum.android.diploma.data.network.DictionariesRequest
import ru.practicum.android.diploma.data.network.DictionariesResponse
import ru.practicum.android.diploma.data.network.VacanciesSearchRequest
import ru.practicum.android.diploma.data.network.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.toVacancy

class VacanciesPagingSource(
    private val networkClient: NetworkClient,
    private val query: String,
    private val getItemCountCallback: (Int) -> Unit
) : PagingSource<Int, Vacancy>() {

    private var currencies: List<CurrencyDto>? = null

    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {
        if (currencies == null) {
            currencies = getCurrencyList()
        }

        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), null, null)
        }

        val pageNumber = params.key ?: 0
        val request = VacanciesSearchRequest(query, pageNumber, emptyMap())
        val response = networkClient.doRequest(request)

        return when (response) {
            is VacanciesSearchResponse -> {
                getItemCountCallback(response.found)

                val nextPage = if (pageNumber < response.pages) pageNumber + 1 else null
                val vacancies = response.items.map { dto ->
                    dto.toVacancy().copy(
                        currencySymbol = currencies?.find { it.code == dto.salary?.currency }?.abbr
                    )
                }
                LoadResult.Page(vacancies, null, nextPage)
            }

            else -> {
                LoadResult.Error(RuntimeException("403"))
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
