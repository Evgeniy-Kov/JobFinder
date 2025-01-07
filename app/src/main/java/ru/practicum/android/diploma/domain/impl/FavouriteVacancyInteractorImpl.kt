package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.api.FavouriteVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavouriteVacancyInteractorImpl(private val repository: FavouriteVacancyRepository) :
    FavouriteVacancyInteractor {
    override fun getAllFavouriteVacancies(): Flow<List<VacancyDetails>> =
        repository.getAllFavouriteVacancies()

    override fun getFavouriteVacancyById(id: String): Flow<VacancyDetails> =
        repository.getFavouriteVacancyById(id)

    override suspend fun addFavouriteVacancy(vacancy: VacancyDetails) {
        repository.addFavouriteVacancy(vacancy)
    }

    override suspend fun deleteFavouriteVacancy(vacancy: VacancyDetails) {
        repository.deleteFavouriteVacancy(vacancy)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return repository.isFavorite(id)
    }
}
