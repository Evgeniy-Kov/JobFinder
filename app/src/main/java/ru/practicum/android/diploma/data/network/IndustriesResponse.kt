package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.IndustryDto

data class IndustriesResponse(
    val industries: List<IndustryDto>
) : Response()
