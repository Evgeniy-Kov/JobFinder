package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsViewModel(private val favouritesInteractor: FavouriteVacancyInteractor) : ViewModel() {
    private val favoriteState = MutableLiveData<Boolean>()
    fun observeFavoriteState(): LiveData<Boolean> = favoriteState
    private val vacancyDetails = MutableLiveData<VacancyDetails>()
    fun observeVacancyDetails(): LiveData<VacancyDetails> = vacancyDetails

    fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            vacancyDetails.postValue(favouritesInteractor.getFavouriteVacancyById(vacancyId).first())
        }
    }

    fun onFavoriteClick(vacancy: VacancyDetails) {
        when (vacancy.isFavourite) {
            true -> {
                viewModelScope.launch(Dispatchers.IO) {
                    favouritesInteractor.deleteFavouriteVacancy(vacancy)
                }
            }

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    favouritesInteractor.addFavouriteVacancy(vacancy)
                }
            }
        }
        favoriteState.postValue(!vacancy.isFavourite)
    }
}
