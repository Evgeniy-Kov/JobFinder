package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.repository.VacanciesPagingSource

interface PagingSourceInteractor {
    fun getVacanciesPagingSource(query: String, filter: Map<String, String>, foundItemsCallback: (Int) -> Unit): VacanciesPagingSource
}
