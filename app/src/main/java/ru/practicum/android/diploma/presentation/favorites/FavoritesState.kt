package ru.practicum.android.diploma.presentation.favorites

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed interface FavoritesState {

    data object Loading : FavoritesState

    data class Content(
        val vacanciesList: List<VacancyDetails>
    ) : FavoritesState

    data object Error : FavoritesState

    data object Empty : FavoritesState

}
