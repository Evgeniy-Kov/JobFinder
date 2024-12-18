package ru.practicum.android.diploma.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor

class FavoritesViewModel(
    private val favouriteVacancyInteractor: FavouriteVacancyInteractor
) : ViewModel() {

    private val _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState> get() = _favoritesState

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favouriteVacancyInteractor.getAllFavouriteVacancies()
                .collect { favoriteVacancies ->
                    if (favoriteVacancies.isEmpty()) {
                        _favoritesState.value = FavoritesState.Empty("No favorite vacancies found.")
                    } else {
                        _favoritesState.value = FavoritesState.Content(favoriteVacancies)
                    }
                }
        }
    }
}
