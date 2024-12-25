package ru.practicum.android.diploma.ui.search

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.practicum.android.diploma.App.Companion.FILTER_PREFERENCES_KEY
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val pagingSourceInteractor: PagingSourceInteractor,
    private val sharedPrefInteractor: SharedPreferencesInteractor,
) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _preferenceUpdates = MutableLiveData<Filter?>()
    val preferenceUpdates: LiveData<Filter?>
        get() = _preferenceUpdates

    private var latestSearchText: String = ""

    private var searchJob: Job? = null

    private val jobSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            setQuery(changedText)
        }

    private var newPagingSource: PagingSource<*, *>? = null

    val vacancies: Flow<PagingData<Vacancy>> = query.asFlow()
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)

    private val _itemCountLivedata = MutableLiveData<Int>()
    val itemCountLivedata: LiveData<Int>
        get() = _itemCountLivedata

    fun clearLatestSearchText() {
        latestSearchText = ""
        setQuery("")
    }

    private fun setQuery(query: String) {
        if (query.isNotBlank()) _query.value = query
    }

    private val getItemCountCallback: (Int) -> Unit = { count -> _itemCountLivedata.value = count }

    private fun newPager(query: String): Pager<Int, Vacancy> {
        return Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false, prefetchDistance = PAGE_SIZE / 2)) {
            pagingSourceInteractor.getVacanciesPagingSource(query, getItemCountCallback)
                .also { newPagingSource = it }
        }
    }

    fun searchDebounce(changedText: String) {
        stopSearch()
        if (latestSearchText == changedText || changedText.isEmpty()) {
            return
        }
        latestSearchText = changedText
        jobSearchDebounce(changedText)
    }

    fun stopSearch() {
        searchJob?.cancel()
    }
    fun saveFilter(filter: Filter) = sharedPrefInteractor.saveFilter(filter)
    fun clearFilter() = sharedPrefInteractor.clearFilter()

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                FILTER_PREFERENCES_KEY -> {
                    val newValue = sharedPrefInteractor.loadFilter()
                    _preferenceUpdates.postValue(newValue)
                }
            }
        }

    init {
        sharedPrefInteractor.setPreferencesListener(preferenceChangeListener)
        _preferenceUpdates.postValue(sharedPrefInteractor.loadFilter())
    }

    override fun onCleared() {
        super.onCleared()
        sharedPrefInteractor.deletePreferencesListener(preferenceChangeListener)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val PAGE_SIZE = 20
    }
}
