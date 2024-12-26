package ru.practicum.android.diploma.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.App.Companion.FILTER_PREFERENCES_KEY
import ru.practicum.android.diploma.domain.api.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Filter

class SharedPreferencesRepositoryImpl(
    private val preferences: SharedPreferences,
) : SharedPreferencesRepository {
    override fun savePreferences(filter: Filter) {
        preferences.edit {
            putString(FILTER_PREFERENCES_KEY, Gson().toJson(filter))
        }
    }

    override fun loadPreferences(): Filter? {
        val filterType = object : TypeToken<Filter>() {}.type
        val json = preferences.getString(FILTER_PREFERENCES_KEY, null)
        if (json != null) {
            return Gson().fromJson(json, filterType)
        }
        return null
    }

    override fun deletePreferences() {
        preferences.edit {
            remove(FILTER_PREFERENCES_KEY)
        }
    }

    override fun setPreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun deletePreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
