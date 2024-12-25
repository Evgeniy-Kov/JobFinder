package ru.practicum.android.diploma.domain.api

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.models.Filter

interface SharedPreferencesRepository {
    fun savePreferences(filter: Filter)
    fun loadPreferences(): Filter?
    fun deletePreferences()
    fun setPreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
    fun deletePreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
}
