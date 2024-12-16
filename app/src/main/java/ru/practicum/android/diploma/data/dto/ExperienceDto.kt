package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 *  @param name Название опыта работы
 */

data class ExperienceDto(
    @SerializedName("name") val name: String
)
