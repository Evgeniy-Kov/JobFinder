package ru.practicum.android.diploma.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavouritesViewModel(private val favouritesInteractor: FavouriteVacancyInteractor) : ViewModel() {

    private val favouriteVacancies = MutableLiveData<List<VacancyDetails>>()
    fun getAllFavouriteVacancies(): LiveData<List<VacancyDetails>> = favouriteVacancies

    private fun refreshFavouriteVacancies() {
        viewModelScope.launch {
            favouritesInteractor.getAllFavouriteVacancies().collect { vacancyList ->
                favouriteVacancies.postValue(vacancyList)
            }
        }
    }

    init {
        refreshFavouriteVacancies()
    }
}
