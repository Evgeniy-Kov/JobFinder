package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.AreaRequest
import ru.practicum.android.diploma.data.network.AreaResponse
import ru.practicum.android.diploma.domain.api.AreasRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.util.toArea

class AreasRepositoryImpl(
    private val networkClient: NetworkClient
) : AreasRepository {

    override suspend fun getAreas(): Resource<List<Area>> {
        val request = AreaRequest()
        val response = networkClient.doRequest(request)

        return when (response) {
            is AreaResponse -> {
                Resource.Success(response.areasDto.map { it.toArea() })
            }

            else -> {
                Resource.Error(response.resultCode)
            }
        }
    }
}
