package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity

@Dao
interface FavouriteVacancyDao {
    @Query("SELECT * FROM favorite_table")
    fun getAllVacancies(): Flow<List<FavouriteVacancyEntity>>

    @Query("SELECT * FROM favorite_table WHERE vacancyId = :id")
    suspend fun getVacancyById(id: String): FavouriteVacancyEntity

    @Query("DELETE FROM favorite_table WHERE vacancyId = :id")
    suspend fun deleteVacancyById(id: String)

    @Insert(entity = FavouriteVacancyEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVacancy(vacancy: FavouriteVacancyEntity)

    @Query("SELECT COUNT(*) > 0 FROM favorite_table WHERE vacancyId = :id")
    suspend fun isFavorite(id: String): Boolean
}
