package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.util.isInternetAvailable
import java.net.SocketTimeoutException
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
                getResponse(dto)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
                Response(NetworkClient.SERVER_ERROR)
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                Response(NetworkClient.SERVER_ERROR)
            }
        }
    }

    private suspend fun getResponse(dto: Any): Response {
        return when (dto) {
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
                getAreas()
            }

            is DictionariesRequest -> {
                getDictionaries()
            }

            else -> {
                Response(NetworkClient.BAD_REQUEST)
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

    private suspend fun getAreas(): AreaResponse {
        return AreaResponse(hhApiService.getAreas())
            .apply { resultCode = NetworkClient.SUCCESS }
    }

    private suspend fun getDictionaries(): DictionariesResponse {
        return DictionariesResponse(hhApiService.getDictionaries()).apply { resultCode = NetworkClient.SUCCESS }
    }
}
