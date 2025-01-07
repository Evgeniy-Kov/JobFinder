package ru.practicum.android.diploma.domain.api

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.models.Filter

interface SharedPreferencesInteractor {
    fun saveFilter(filter: Filter)
    fun loadFilter(): Filter?
    fun clearFilter()
    fun setPreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
    fun deletePreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
}
