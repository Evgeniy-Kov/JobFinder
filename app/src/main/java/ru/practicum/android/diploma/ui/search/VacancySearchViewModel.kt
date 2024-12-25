package ru.practicum.android.diploma.ui.search

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
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val pagingSourceInteractor: PagingSourceInteractor,
    private val industriesInteractor: IndustriesInteractor
) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _industriesList = MutableLiveData<Resource<List<Industry>>>()
    val industriesList: LiveData<Resource<List<Industry>>> = _industriesList

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

    fun getIndustries() {
        viewModelScope.launch {
            _industriesList.postValue(industriesInteractor.getIndustries())
        }

    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val PAGE_SIZE = 20
    }

}
