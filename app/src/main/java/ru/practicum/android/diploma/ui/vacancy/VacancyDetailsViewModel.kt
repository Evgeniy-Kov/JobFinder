package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsViewModel(
    private val favouritesInteractor: FavouriteVacancyInteractor,
    private val detailsInteractor: VacancyDetailsInteractor,
) : ViewModel() {
    private val favoriteState = MutableLiveData<Boolean>()
    fun observeFavoriteState(): LiveData<Boolean> = favoriteState
    private val vacancyDetails = MutableLiveData<Resource<VacancyDetails>>()
    fun observeVacancyDetails(): LiveData<Resource<VacancyDetails>> = vacancyDetails

    fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            if (favouritesInteractor.isFavorite(vacancyId)) {
                vacancyDetails.postValue(
                    Resource.Success(favouritesInteractor.getFavouriteVacancyById(vacancyId).first())
                )
            } else {
                vacancyDetails.postValue(detailsInteractor.getVacancyDetails(vacancyId))
            }
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
