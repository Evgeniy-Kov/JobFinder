package ru.practicum.android.diploma.data.network

data class VacanciesSearchRequest(
    val searchRequest: String,
    val pageNumber: Int,
    val options: Map<String, String>
)
