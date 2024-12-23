package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Resource

interface AreasRepository {
    suspend fun getAreas(): Resource<List<Area>>
}
