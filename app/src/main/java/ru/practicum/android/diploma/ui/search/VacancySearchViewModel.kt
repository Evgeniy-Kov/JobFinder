package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.api.VacancySearchInteractor
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val vacancySearchInteractor: VacancySearchInteractor,
    private val pagingSourceInteractor: PagingSourceInteractor
) : ViewModel() {

    private var latestSearchText: String = ""

    private var searchJob: Job? = null

    private val searchState = MutableLiveData<SearchState>()
    fun observeSearchState(): LiveData<SearchState> = searchState

    private val jobSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            searchRequest(changedText)
        }

    init {
        renderState(SearchState.Default)
    }

    private fun renderState(state: SearchState) {
        searchState.postValue(state)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isEmpty() || newSearchText.length < 2) {
            return
        }
        renderState(SearchState.Loading)
        searchJob = viewModelScope.launch {
            processResult(vacancySearchInteractor.searchVacancies(newSearchText, 0, hashMapOf()))
        }
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
