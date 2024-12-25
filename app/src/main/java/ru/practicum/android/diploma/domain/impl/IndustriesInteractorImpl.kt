package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Resource

class IndustriesInteractorImpl(private val industriesRepository: IndustriesRepository) : IndustriesInteractor {
    override suspend fun getIndustries(): Resource<List<Industry>> {
        return industriesRepository.getIndustries()
    }
}
