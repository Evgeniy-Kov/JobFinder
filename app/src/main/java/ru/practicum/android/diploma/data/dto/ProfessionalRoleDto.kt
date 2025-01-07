package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 *  @param name Хранит информацию о названии профессиональной роли
 */

data class ProfessionalRoleDto(
    @SerializedName("name") val name: String
)
