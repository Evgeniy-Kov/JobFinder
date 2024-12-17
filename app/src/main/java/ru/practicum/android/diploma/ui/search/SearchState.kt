package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchState {
    data object Loading : SearchState
    data class ContentSearch(val jobs: List<Vacancy>) : SearchState
    data class Error(val errorMessage: String) : SearchState
    data object NothingFound : SearchState
    data object Default : SearchState
}
