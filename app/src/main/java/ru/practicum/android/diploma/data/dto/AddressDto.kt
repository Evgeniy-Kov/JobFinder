package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param city `Nullable` Хранит информацию о названии города
 * @param street `Nullable` Хранит информацию о названии улицы
 * @param building `Nullable` Хранит информацию о номере дома
 */

data class AddressDto(
    @SerializedName("city") val city: String?,
    @SerializedName("street") val street: String?,
    @SerializedName("building") val building: String?
)
