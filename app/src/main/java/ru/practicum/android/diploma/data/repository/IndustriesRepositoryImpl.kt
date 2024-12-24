package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.IndustriesRequest
import ru.practicum.android.diploma.data.network.IndustriesResponse
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.util.toIndustry

class IndustriesRepositoryImpl(private val networkClient: NetworkClient) : IndustriesRepository {
    override suspend fun getIndustries(): Resource<List<Industry>> {
        val response = networkClient.doRequest(IndustriesRequest())
        if (response.resultCode == NetworkClient.SUCCESS) {
            val industriesList = (response as IndustriesResponse).industries.map { it.toIndustry() }
            return Resource.Success(industriesList)
        } else {
            return Resource.Error(response.resultCode)
        }
    }
}
