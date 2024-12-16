package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param name Обязательное поле, хранит информацию о названии ключевого навыка
 */

data class KeySkillDto(
    @SerializedName("name") val name: String
)
