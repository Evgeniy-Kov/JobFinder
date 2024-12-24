package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter

interface SharedPreferencesRepository {
    fun savePreferences(filter: Filter)
    fun loadPreferences(): Filter
    fun deletePreferences()
}
