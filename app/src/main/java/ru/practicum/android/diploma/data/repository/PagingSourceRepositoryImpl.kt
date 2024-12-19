package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.domain.api.PagingSourceRepository

class PagingSourceRepositoryImpl(
    private val networkClient: NetworkClient
) : PagingSourceRepository {
    override fun getVacanciesPagingSource(query: String): VacanciesPagingSource {
        return VacanciesPagingSource(networkClient, query)
    }
}
