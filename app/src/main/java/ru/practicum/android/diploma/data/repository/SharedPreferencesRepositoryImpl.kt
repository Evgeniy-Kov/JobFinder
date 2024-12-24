package ru.practicum.android.diploma.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.api.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Filter

class SharedPreferencesRepositoryImpl(
    private val preferences: SharedPreferences,
) : SharedPreferencesRepository {
    override fun savePreferences(filter: Filter) {
        preferences.edit {
            putString(PREFERENCES_KEY, Gson().toJson(filter))
        }
    }

    override fun loadPreferences(): Filter {
        val filterType = object : TypeToken<Filter>() {}.type
        val json = preferences.getString(PREFERENCES_KEY, null)
        if (json != null) {
            return Gson().fromJson(json, filterType)
        }
        return Filter()
    }

    override fun deletePreferences() {
        preferences.edit {
            remove(PREFERENCES_KEY)
        }
    }

    companion object {
        private const val PREFERENCES_KEY = "filter_preferences"
    }
}
