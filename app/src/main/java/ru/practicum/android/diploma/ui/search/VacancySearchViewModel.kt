package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val pagingSourceInteractor: PagingSourceInteractor
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var latestSearchText: String = ""

    private var searchJob: Job? = null

    private val searchState = MutableLiveData<SearchState>()
    fun observeSearchState(): LiveData<SearchState> = searchState

    private val jobSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            setQuery(changedText)
        }

    private var newPagingSource: PagingSource<*, *>? = null

    val vacancies: Flow<PagingData<Vacancy>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)

    init {
        renderState(SearchState.Default)
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }

    private fun newPager(query: String): Pager<Int, Vacancy> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            pagingSourceInteractor.getVacanciesPagingSource(query).also { newPagingSource = it }
        }
    }

    private fun renderState(state: SearchState) {
        searchState.postValue(state)
    }

    private fun processResult(searchResult: Resource<List<Vacancy>>) {
        when (searchResult) {
            is Resource.Success -> {
                val jobs = searchResult.data
                if (jobs.isNullOrEmpty()) {
                    renderState(SearchState.NothingFound)
                } else {
                    renderState(SearchState.ContentSearch(jobs))
                }
            }

            is Resource.Error -> {
                val errorMessage = searchResult.message
                renderState(SearchState.Error("Код ошибки: " + errorMessage.toString()))
            }

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

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}
