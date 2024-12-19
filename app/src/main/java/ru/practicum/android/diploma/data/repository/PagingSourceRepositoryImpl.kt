package ru.practicum.android.diploma.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.domain.api.PagingSourceRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class PagingSourceRepositoryImpl(
    private val networkClient: NetworkClient
) : PagingSourceRepository {
    override fun getPagedData(query: String): Flow<PagingData<Vacancy>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { VacanciesPagingSource(networkClient, query) }
        ).flow
    }
}
