package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.CurrencyDto
import ru.practicum.android.diploma.data.network.DictionariesRequest
import ru.practicum.android.diploma.data.network.DictionariesResponse
import ru.practicum.android.diploma.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.data.network.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.toVacancyDetails

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyDetailsRepository {

    private var currencies: List<CurrencyDto>? = null

    override suspend fun getVacancyDetails(vacancyId: String): Resource<VacancyDetails> {
        if (currencies == null) {
            currencies = getCurrencyList()
        }
        val request = VacancyDetailsRequest(vacancyId)
        val response = networkClient.doRequest(request)

        return when (response) {
            is VacancyDetailsResponse -> {
                val dto = response.vacancyDetailsDto
                val vacancyDetails = dto.toVacancyDetails().copy(
                    currencySymbol = currencies?.find { it.code == dto.salary?.currency }?.abbr
                )
                Resource.Success(vacancyDetails)
            }

            else -> {
                Resource.Error(response.resultCode)
            }
        }
    }

    private suspend fun getCurrencyList(): List<CurrencyDto> {
        val response = networkClient.doRequest(DictionariesRequest())
        val currencyList = mutableListOf<CurrencyDto>()
        if (response.resultCode == NetworkClient.SUCCESS) {
            currencyList.addAll((response as DictionariesResponse).dictionary.currency)
        }
        return currencyList
    }
}
