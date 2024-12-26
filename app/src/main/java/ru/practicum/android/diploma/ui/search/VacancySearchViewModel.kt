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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.IndustriesInteractor
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.AreasInteractor
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.region.AreaScreenState
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val pagingSourceInteractor: PagingSourceInteractor,
    private val industriesInteractor: IndustriesInteractor,
    private val areasInteractor: AreasInteractor,
) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _industriesList = MutableLiveData<Resource<List<Industry>>>()
    val industriesList: LiveData<Resource<List<Industry>>> = _industriesList

    private val _areaScreenState = MutableLiveData<AreaScreenState>(AreaScreenState.Content)
    val areaScreenState: LiveData<AreaScreenState>
        get() = _areaScreenState

    private val _countries = MutableLiveData<List<Area>>(emptyList())
    val countries: LiveData<List<Area>> = _countries

    private val _countryId = MutableStateFlow<String>("")
    val countryId: StateFlow<String> = _countryId.asStateFlow()

    private val _regionNameFilter = MutableStateFlow<String>("")
    val regionNameFilter: StateFlow<String> = _regionNameFilter.asStateFlow()

    val regs = countryId.map(::filterAreaByParentId)

    val regions = regionNameFilter.map(::filterAreaByQuery)

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

    fun setCountryId(countryId: String) {
        _countryId.value = countryId
    }

    fun setRegionNameFilter(regionNameFilter: String) {
        _regionNameFilter.tryEmit(regionNameFilter)
    }

    fun getAreas() {
        _areaScreenState.value = AreaScreenState.Loading
        viewModelScope.launch {
            val resource = areasInteractor.getAreas()
            processResult(resource)
        }
    }

    private fun setQuery(query: String) {
        if (query.isNotBlank()) _query.value = query
    }

    private suspend fun filterAreaByQuery(query: String): List<Area> {
        val list = regs.first()
        if (query.isBlank()) {
            return list
        }
        val filteredList = list.filter { it.name.lowercase().contains(query.lowercase()) }
        if (filteredList.isEmpty()) {
            _areaScreenState.value = AreaScreenState.Empty
        } else {
            _areaScreenState.value = AreaScreenState.Content
        }
        return filteredList

    }

    private val getItemCountCallback: (Int) -> Unit = { count -> _itemCountLivedata.value = count }

    private fun newPager(query: String): Pager<Int, Vacancy> {
        return Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false, prefetchDistance = PAGE_SIZE / 2)) {
            pagingSourceInteractor.getVacanciesPagingSource(query, getItemCountCallback)
                .also { newPagingSource = it }
        }
    }

    private fun filterAreaByParentId(id: String): List<Area> {
        val countryList = countries.value ?: emptyList()

        val result = mutableListOf<Area>()

        if (id == "") {
            countryList.forEach { result.addAll(getAllAreas(it)) }
        } else {
            countryList.forEach { if (it.id == id) result.addAll(getAllAreas(it)) }
        }
        return result
    }

    private fun getAllAreas(area: Area): List<Area> {
        val allAreas = mutableListOf<Area>()
        if (area.parentId != null) allAreas.add(area)
        for (subArea in area.areas) {
            allAreas.addAll(getAllAreas(subArea))
        }
        return allAreas.sortedBy { it.name }
    }

    private fun processResult(result: Resource<List<Area>>) {
        if (areaScreenState.value != AreaScreenState.Loading) {
            return
        }

        when (result) {
            is Resource.Success<List<Area>> -> {
                _countries.value = result.data
                _areaScreenState.value = AreaScreenState.Content
            }

            is Resource.Error -> {
                _areaScreenState.value = AreaScreenState.Error(result.message)
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
