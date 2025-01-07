package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.DictionaryDto

data class DictionariesResponse(
    val dictionary: DictionaryDto
) : Response()
