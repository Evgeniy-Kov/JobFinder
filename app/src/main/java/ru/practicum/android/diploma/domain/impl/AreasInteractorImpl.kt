package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.AreasInteractor
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Resource

class AreasInteractorImpl(private val areasRepository: AreasRepository) : AreasInteractor {

    override suspend fun getAreas(): Resource<List<Area>> {
        return areasRepository.getAreas()
    }
}
