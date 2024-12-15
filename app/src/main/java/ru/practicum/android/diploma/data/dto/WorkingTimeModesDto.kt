package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 *  @param name Хранит информацию о режиме времени работы
 */

class WorkingTimeModesDto(
    @SerializedName("name") val name: String
)
