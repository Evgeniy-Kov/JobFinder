package ru.practicum.android.diploma.ui.region

sealed interface AreaScreenState {
    data object Loading : AreaScreenState
    data object Content : AreaScreenState
    data object Empty : AreaScreenState
    data class Error(val message: Int) : AreaScreenState
}
