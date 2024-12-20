package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.util.isInternetAvailable
import java.net.UnknownHostException

class RetrofitNetworkClient(
    private val hhApiService: HhApi,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isInternetAvailable(context)) {
            return Response(NetworkClient.NO_CONNECTION)
        }

        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesSearchRequest -> {
                        getVacancies(dto)
                    }

                    is VacancyDetailsRequest -> {
                        getVacancyDetails(dto)
                    }

                    is IndustriesRequest -> {
                        getIndustries()
                    }

                    is CountriesRequest -> {
                        getCountries()
                    }

                    is AreaRequest -> {
                        getAreaById(dto)
                    }

                    is DictionariesRequest -> {
                        getDictionaries()
                    }

                    else -> {
                        Response(NetworkClient.BAD_REQUEST)
                    }
                }
            } catch (e: UnknownHostException) {
                e.printStackTrace()
                Response(NetworkClient.SERVER_ERROR)
            }
        }
    }

    private suspend fun getVacancies(request: VacanciesSearchRequest): VacanciesSearchResponse {
        return hhApiService.getVacancies(request.searchRequest, request.pageNumber, request.options)
            .apply { resultCode = NetworkClient.SUCCESS }
    }

    private suspend fun getVacancyDetails(request: VacancyDetailsRequest): VacancyDetailsResponse {
        return VacancyDetailsResponse(hhApiService.getVacancyDetails(request.vacancyId))
            .apply { resultCode = NetworkClient.SUCCESS }
    }

    private suspend fun getIndustries(): IndustriesResponse {
        return IndustriesResponse(hhApiService.getIndustries()).apply { resultCode = NetworkClient.SUCCESS }
    }

    private suspend fun getCountries(): CountriesResponse {
        return CountriesResponse(hhApiService.getCountries()).apply { resultCode = NetworkClient.SUCCESS }
    }

    private suspend fun getAreaById(request: AreaRequest): AreaResponse {
        return AreaResponse(hhApiService.getAreaById(request.areaId))
            .apply { resultCode = NetworkClient.SUCCESS }
    }

    private suspend fun getDictionaries(): DictionariesResponse {
        return DictionariesResponse(hhApiService.getDictionaries()).apply { resultCode = NetworkClient.SUCCESS }
    }
}
