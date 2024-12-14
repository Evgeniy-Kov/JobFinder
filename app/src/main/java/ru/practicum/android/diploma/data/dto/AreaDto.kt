package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaDto(
    @SerializedName("parent_id")val parentId: String,
    @SerializedName("id")val id: String,
    @SerializedName("name")val name: String,
    @SerializedName("areas")val areas: List<AreaDto>
)
