package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 *  @param name Хранит информацию о название профессиональной роли
 */

data class ProfessionalRoleDto(
    @SerializedName("name") val name: String
)
