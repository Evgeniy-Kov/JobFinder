package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.domain.api.PagingSourceRepository

class PagingSourceRepositoryImpl(
    private val networkClient: NetworkClient
) : PagingSourceRepository {
    override fun getVacanciesPagingSource(
        query: String,
        filter: Map<String, String>,
        foundItemsCallback: (Int) -> Unit
    ): VacanciesPagingSource {
        return VacanciesPagingSource(networkClient, query, filter, foundItemsCallback)
    }
}
