package ru.practicum.android.diploma.domain.impl

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.api.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Filter

class SharedPreferencesInteractorImpl(private val repository: SharedPreferencesRepository) :
    SharedPreferencesInteractor {

    override fun saveFilter(filter: Filter) {
        repository.savePreferences(filter)
    }

    override fun loadFilter(): Filter? {
        return repository.loadPreferences()
    }

    override fun clearFilter() {
        repository.deletePreferences()
    }

    override fun setPreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        repository.setPreferencesListener(listener)
    }

    override fun deletePreferencesListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        repository.deletePreferencesListener(listener)
    }
}
