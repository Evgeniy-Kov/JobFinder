package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity
import ru.practicum.android.diploma.domain.api.FavouriteVacancyRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavouriteVacancyRepositoryImpl(
    private val database: AppDatabase,
    private val vacancyDBConverter: VacancyDbConverter,
) : FavouriteVacancyRepository {
    override fun getAllFavouriteVacancies(): Flow<List<VacancyDetails>> = flow {
        database.favouriteVacancyDao().getAllVacancies().collect { vacancies ->
            emit(convertFromFavouriteVacancyEntity(vacancies))
        }
    }

    override fun getFavouriteVacancyById(id: String): Flow<VacancyDetails> = flow {
        emit(vacancyDBConverter.convert(database.favouriteVacancyDao().getVacancyById(id)))
    }

    override suspend fun addFavouriteVacancy(vacancy: VacancyDetails) {
        database.favouriteVacancyDao().insertVacancy(vacancyDBConverter.convert(vacancy))
    }

    override suspend fun deleteFavouriteVacancy(vacancy: VacancyDetails) {
        database.favouriteVacancyDao().deleteVacancyById(vacancy.id)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return database.favouriteVacancyDao().isFavorite(id)
    }

    private fun convertFromFavouriteVacancyEntity(vacancies: List<FavouriteVacancyEntity>): List<VacancyDetails> {
        return vacancies.map { vacancy -> vacancyDBConverter.convert(vacancy) }
    }
}
