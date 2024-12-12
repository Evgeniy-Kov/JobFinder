package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity

@Database(
    version = 1,
    entities = [FavouriteVacancyEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteVacancyDao(): FavouriteVacancyDao
}
