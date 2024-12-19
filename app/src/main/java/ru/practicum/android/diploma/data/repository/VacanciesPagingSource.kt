package ru.practicum.android.diploma.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.VacanciesSearchRequest
import ru.practicum.android.diploma.data.network.VacanciesSearchResponse
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.toVacancy

class VacanciesPagingSource(
    private val networkClient: NetworkClient,
    private val query: String
) : PagingSource<Int, Vacancy>() {
    override fun getRefreshKey(state: PagingState<Int, Vacancy>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Vacancy> {

        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), null, null)
        }

        val pageNumber = params.key ?: 0
        val request = VacanciesSearchRequest(query, pageNumber, emptyMap())
        val response = networkClient.doRequest(request)


        return when (response) {
            is VacanciesSearchResponse -> {
                response.found
                val nextPage = if (pageNumber < response.pages) pageNumber + 1 else null
                val vacancies = response.items.map { it.toVacancy() }
                LoadResult.Page(vacancies, null, nextPage)
            }

            else -> {
                LoadResult.Error(RuntimeException("403"))
            }
        }
    }
}
