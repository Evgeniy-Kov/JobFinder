package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 *  @param name Хранит информацию о названии интервала работы
 */

class WorkingTimeIntervalsDto(
    @SerializedName("name") val name: String
)
