package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {
    override suspend fun getVacancyDetails(vacancyId: String): Resource<VacancyDetails> {
        return repository.getVacancyDetails(vacancyId)
    }
}
