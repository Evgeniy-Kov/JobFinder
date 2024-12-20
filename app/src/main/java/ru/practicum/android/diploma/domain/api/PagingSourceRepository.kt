package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.repository.VacanciesPagingSource

interface PagingSourceRepository {
    fun getVacanciesPagingSource(query: String, foundItemsCallback: (Int) -> Unit): VacanciesPagingSource
}
