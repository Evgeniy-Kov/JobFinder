package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.network.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response

    companion object {
        const val NO_CONNECTION = -1
        const val SUCCESS = 200
        const val BAD_REQUEST = 400
        const val SERVER_ERROR = 500
    }
}
