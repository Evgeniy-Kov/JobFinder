package ru.practicum.android.diploma.domain.api

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface PagingSourceInteractor {
    fun getPagedData(query: String): Flow<PagingData<Vacancy>>
}
