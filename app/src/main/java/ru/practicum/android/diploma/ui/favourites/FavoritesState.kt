package ru.practicum.android.diploma.ui.favourites

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed interface FavoritesState {

    data class Content(
        val vacanciesList: List<VacancyDetails>
    ) : FavoritesState
    data class Error(val errorMessage: String) : FavoritesState
    data class Empty(val emptyMessage: String) : FavoritesState

}
