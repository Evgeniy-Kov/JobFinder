package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavouriteVacancyEntity(
    @PrimaryKey
    val vacancyId: String,
)
