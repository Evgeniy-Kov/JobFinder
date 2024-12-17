package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.models.Resource


class FavoritesViewModel(
    private val favoritesInteractor: FavouriteVacancyInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<FavoritesState>()
    fun getStateLiveData(): LiveData<FavoritesState> = stateLiveData

    private fun renderState(state: FavoritesState) {
        stateLiveData.postValue(state)
    }

    fun updateFavoritesList() {
        viewModelScope.launch {
            renderState(FavoritesState.Loading)
            favoritesInteractor.getAllFavouriteVacancies().collect { it ->
                when (it) {
                    is Resource.Success -> {
                        if (it.data.isEmpty()) {
                            renderState(FavoritesState.Empty)
                        } else {
                            renderState(FavoritesState.Content(it.data))
                        }
                    }

                    is Resource.Error -> {
                        renderState(FavoritesState.Error)
                    }
                }
            }
        }
    }
}
