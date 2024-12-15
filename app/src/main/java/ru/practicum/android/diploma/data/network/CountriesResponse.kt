package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.AreaRegionDto

data class CountriesResponse(
    val countries: List<AreaRegionDto>
) : Response()
