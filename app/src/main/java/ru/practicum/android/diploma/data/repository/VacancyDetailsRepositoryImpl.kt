package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.data.network.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.toVacancyDetails

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyDetailsRepository {
    override suspend fun getVacancyDetails(vacancyId: String): Resource<VacancyDetails> {
        val request = VacancyDetailsRequest(vacancyId)
        val response = networkClient.doRequest(request)

        return when (response) {
            is VacancyDetailsResponse -> {
                val vacancyDetails = response.vacancyDetailsDto.toVacancyDetails()
                Resource.Success(vacancyDetails)
            }

            else -> {
                Resource.Error(response.resultCode)
            }
        }
    }
}
