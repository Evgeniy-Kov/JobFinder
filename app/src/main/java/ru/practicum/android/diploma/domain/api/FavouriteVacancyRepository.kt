package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavouriteVacancyRepository {
    fun getAllFavouriteVacancies(): Flow<List<VacancyDetails>>
    suspend fun getFavouriteVacancyById(id: String): Flow<VacancyDetails>
    suspend fun addFavouriteVacancy(vacancy: VacancyDetails)
    suspend fun deleteFavouriteVacancy(vacancy: VacancyDetails)
    suspend fun isFavorite(id: String): Boolean
}
