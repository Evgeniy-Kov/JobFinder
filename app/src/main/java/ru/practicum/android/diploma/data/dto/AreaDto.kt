package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param parentId `Nullable` Хранит идентификатор родительского региона. Если регион - это страна, `parentId = null`
 * @param id Обязательное поле, хранит идентификатор региона
 * @param name Обязательное поле, хранит информацию о названии улицы
 * @param areas Обязательное поле, хранит массив экземпляров класса `AreaDto` с информацией о дочерних регионах,
 * если регион - это населённый пункт, массив будет пустым
 */

data class AreaDto(
    @SerializedName("parent_id") val parentId: String?,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("areas") val areas: List<AreaDto>
)
