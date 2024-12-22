package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Resource

interface IndustriesRepository {
    suspend fun getIndustries(query: String): Resource<List<Industry>>
}
