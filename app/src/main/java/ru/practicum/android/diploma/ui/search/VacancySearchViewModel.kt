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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.App.Companion.FILTER_PREFERENCES_KEY
import ru.practicum.android.diploma.domain.api.AreasInteractor
import ru.practicum.android.diploma.domain.api.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.industry.IndustryScreenState
import ru.practicum.android.diploma.ui.region.AreaScreenState
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.parseFilter
import ru.practicum.android.diploma.util.toCountry

class VacancySearchViewModel(
    private val pagingSourceInteractor: PagingSourceInteractor,
    private val industriesInteractor: IndustriesInteractor,
    private val sharedPrefInteractor: SharedPreferencesInteractor,
    private val areasInteractor: AreasInteractor,
) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _preferenceUpdates = MutableLiveData<Filter>()
    val preferenceUpdates: LiveData<Filter>
        get() = _preferenceUpdates

    var latestSearchFilter = Filter()
        private set

    private val _industriesList = MutableLiveData<List<Industry>>(emptyList())
    val industriesList: LiveData<List<Industry>> = _industriesList

    private val _industryScreenState = MutableLiveData<IndustryScreenState>(IndustryScreenState.Content)
    val industryScreenState: LiveData<IndustryScreenState>
        get() = _industryScreenState

    private val _areaScreenState = MutableLiveData<AreaScreenState>(AreaScreenState.Content)
    val areaScreenState: LiveData<AreaScreenState>
        get() = _areaScreenState

    private val _searchScreenState = MutableLiveData<SearchScreenState>(SearchScreenState.Default)
    val searchScreenState: LiveData<SearchScreenState>
        get() = _searchScreenState

    private val _countries = MutableLiveData<List<Area>>(emptyList())
    val countries: LiveData<List<Area>> = _countries

    private val _countryId = MutableStateFlow<String>("")
    val countryId: StateFlow<String> = _countryId.asStateFlow()

    private val _regionNameFilter = MutableStateFlow<String>("")
    val regionNameFilter: StateFlow<String> = _regionNameFilter.asStateFlow()

    val regs = countryId.map(::filterAreaByParentId)

    val regions = regionNameFilter.map(::filterAreaByQuery)

    private val _chosenCountry = MutableLiveData<Country?>()
    val chosenCountry: LiveData<Country?>
        get() = _chosenCountry

    private val _chosenRegion = MutableLiveData<Region?>()
    val chosenRegion: LiveData<Region?>
        get() = _chosenRegion

    private val _industryNameFilter = MutableStateFlow<String>("")
    val industryNameFilter: StateFlow<String> = _industryNameFilter.asStateFlow()

    val industry = industryNameFilter.map(::filterIndustryByQuery)

    private var latestSearchText: String = ""

    private var searchJob: Job? = null

    private val _currentFilter = MutableLiveData<Filter>(Filter())
    val currentFilter: LiveData<Filter> = _currentFilter

    private val jobSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText -> setQuery(changedText) }

    private var newPagingSource: PagingSource<*, *>? = null

    val vacancies: Flow<PagingData<Vacancy>> = query.asFlow()
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)

    private val _itemCountLivedata = MutableLiveData<Int>()
    val itemCountLivedata: LiveData<Int>
        get() = _itemCountLivedata

    fun setSearchScreenState(state: SearchScreenState) { _searchScreenState.value = state }

    fun updateLatestSearchFilter() { latestSearchFilter = preferenceUpdates.value ?: Filter() }

    fun retrySearchQueryWithFilterOptions() {
        updateLatestSearchFilter()
        if (latestSearchText.isNotBlank()) setQuery(latestSearchText)
    }

    fun setChosenCountry(country: Country?) {
        _countryId.value = country?.id ?: ""
        _chosenCountry.value = country
    }

    fun setChosenRegion(region: Region?) { _chosenRegion.value = region }

    fun clearLatestSearchText() {
        latestSearchText = ""
        setQuery("")
    }

    fun clearPlaceOfWork() {
        _currentFilter.value = _currentFilter.value?.copy(country = null, region = null)
        saveFilter()
    }

    fun setPlaceOfWork() {
        _currentFilter.value = _currentFilter.value?.copy(country = chosenCountry.value, region = chosenRegion.value)
        saveFilter()
    }
    fun clearChosenCountry() {
        _chosenCountry.value = null
        _countryId.value = ""
    }

    fun clearChosenRegion() { _chosenRegion.value = null }

    fun setIndustry(industry: Industry?) {
        _currentFilter.value = _currentFilter.value?.copy(industry = industry)
        saveFilter()
    }

    fun setRegionNameFilter(regionNameFilter: String) { _regionNameFilter.tryEmit(regionNameFilter) }

    fun setSalary(salary: Int?) {
        _currentFilter.value = _currentFilter.value?.copy(salary = salary)
        saveFilter()
    }

    fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        _currentFilter.value = _currentFilter.value?.copy(onlyWithSalary = onlyWithSalary)
        saveFilter()
    }

    fun getAreas() {
        _areaScreenState.value = AreaScreenState.Loading
        viewModelScope.launch { processResult(areasInteractor.getAreas()) }
    }

    fun chooseCountryByChosenRegion() {
        val areas = countries.value!!.flatMap { getAllAreas(it, true) }

        val currentId = chosenRegion.value?.id ?: ""
        val countryId = generateSequence(currentId) { id ->
            areas.find { it.id == id }?.parentId
        }.last()
        setChosenCountry(areas.find { it.id == countryId }?.toCountry())
        _countryId.tryEmit(countryId)
    }

    private fun setQuery(query: String) { if (query.isNotBlank()) _query.value = query }

    private suspend fun filterAreaByQuery(query: String): List<Area> {
        val filteredList = regs.first().filter { it.name.lowercase().contains(query.lowercase()) || query.isBlank() }
        if (areaScreenState.value !is AreaScreenState.Error) { _areaScreenState.value =
            if (filteredList.isEmpty()) AreaScreenState.Empty else AreaScreenState.Content }
        return filteredList

    }

    private val getItemCountCallback: (Int) -> Unit = { count -> _itemCountLivedata.value = count }

    private fun newPager(query: String): Pager<Int, Vacancy> {
        val filterOptions = parseFilter(preferenceUpdates.value ?: Filter())
        updateLatestSearchFilter()
        return Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false, prefetchDistance = PAGE_SIZE / 2)) {
            pagingSourceInteractor.getVacanciesPagingSource(query, filterOptions, getItemCountCallback)
                .also { newPagingSource = it }
        }
    }

    private fun filterAreaByParentId(id: String): List<Area> {
        val result = mutableListOf<Area>()
        countries.value!!.forEach { if (it.id == id || id == "") result.addAll(getAllAreas(it, false)) }
        return result
    }

    private fun getAllAreas(area: Area, withCountries: Boolean): List<Area> {
        val allAreas = mutableListOf<Area>()
        if (area.parentId != null || withCountries) allAreas.add(area)
        for (subArea in area.areas) { allAreas.addAll(getAllAreas(subArea, false)) }
        return allAreas.sortedBy { it.name }
    }

    private fun processResult(result: Resource<List<Area>>) {
        if (areaScreenState.value != AreaScreenState.Loading) return

        when (result) {
            is Resource.Success<List<Area>> -> {
                _countries.value = result.data
                _areaScreenState.value = AreaScreenState.Content
            }

            is Resource.Error -> { _areaScreenState.value = AreaScreenState.Error(result.message) }
        }
    }

    fun searchDebounce(changedText: String) {
        stopSearch()
        if (latestSearchText == changedText || changedText.isEmpty()) return
        latestSearchText = changedText
        updateLatestSearchFilter()
        jobSearchDebounce(changedText)
    }

    fun stopSearch() { searchJob?.cancel() }

    fun saveFilter() = sharedPrefInteractor.saveFilter(_currentFilter.value ?: Filter())
    fun clearFilter() {
        sharedPrefInteractor.clearFilter()
        _currentFilter.value = Filter()
    }

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                FILTER_PREFERENCES_KEY -> { _preferenceUpdates.value = sharedPrefInteractor.loadFilter() ?: Filter() }
            }
        }

    override fun onCleared() {
        super.onCleared()
        sharedPrefInteractor.deletePreferencesListener(preferenceChangeListener)
    }

    fun getIndustries() {
        _industryScreenState.value = IndustryScreenState.Loading
        viewModelScope.launch { processIndustryResult(industriesInteractor.getIndustries()) }
    }

    private fun processIndustryResult(result: Resource<List<Industry>>) {
        if (industryScreenState.value != IndustryScreenState.Loading) return

        when (result) {
            is Resource.Success -> {
                _industriesList.value = result.data
                _industryScreenState.value = IndustryScreenState.Content
            }

            is Resource.Error -> { _industryScreenState.value = IndustryScreenState.Error(result.message) }
        }
    }

    fun setIndustryNameFilter(industryNameFilter: String) { _industryNameFilter.tryEmit(industryNameFilter) }

    private fun filterIndustryByQuery(query: String): List<Industry> {
        val list = industriesList.value!!.filter { it.name.lowercase().contains(query.lowercase()) || query.isBlank() }
        if (industryScreenState.value !is IndustryScreenState.Error) { _industryScreenState.value =
            if (list.isEmpty()) IndustryScreenState.Empty else IndustryScreenState.Content }
        return list

    }

    init {
        sharedPrefInteractor.setPreferencesListener(preferenceChangeListener)
        _preferenceUpdates.value = sharedPrefInteractor.loadFilter() ?: Filter()
        _currentFilter.value = preferenceUpdates.value ?: Filter()
        updateLatestSearchFilter()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val PAGE_SIZE = 20
    }
}
