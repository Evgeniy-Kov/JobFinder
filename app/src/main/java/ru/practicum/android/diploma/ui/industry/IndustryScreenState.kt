package ru.practicum.android.diploma.ui.industry

sealed interface IndustryScreenState {
    data object Loading : IndustryScreenState
    data object Content : IndustryScreenState
    data object Empty : IndustryScreenState
    data class Error(val message: Int) : IndustryScreenState
}
