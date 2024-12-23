package ru.practicum.android.diploma.ui.search

sealed interface SearchScreenState {
    data object Loading : SearchScreenState
    data object Content : SearchScreenState
    data class Error(val errorMessage: String) : SearchScreenState
    data object NothingFound : SearchScreenState
    data object Default : SearchScreenState
}
