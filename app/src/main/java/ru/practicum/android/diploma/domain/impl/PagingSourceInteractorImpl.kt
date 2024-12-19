package ru.practicum.android.diploma.domain.impl

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.api.PagingSourceRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class PagingSourceInteractorImpl(
    private val repository: PagingSourceRepository
) : PagingSourceInteractor {
    override fun getPagedData(query: String): Flow<PagingData<Vacancy>> {
        return repository.getPagedData(query)
    }
}
