package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.repository.VacanciesPagingSource
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.api.PagingSourceRepository

class PagingSourceInteractorImpl(
    private val repository: PagingSourceRepository
) : PagingSourceInteractor {
    override fun getVacanciesPagingSource(query: String, foundItemsCallback: (Int) -> Unit): VacanciesPagingSource {
        return repository.getVacanciesPagingSource(query, foundItemsCallback)
    }
}
