package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 *@param name Название типа занятости
 */

data class EmploymentDto(
    @SerializedName("name") val name: String
)
