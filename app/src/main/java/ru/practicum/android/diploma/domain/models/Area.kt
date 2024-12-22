package ru.practicum.android.diploma.domain.models

/**
 * @param parentId `Nullable` Хранит идентификатор родительского региона. Если регион - это страна, `parentId = null`
 * @param id Хранит идентификатор региона
 * @param name Хранит информацию о названии улицы
 * @param areas Хранит массив экземпляров класса `Area` с информацией о дочерних регионах,
 * если регион - это населённый пункт, массив будет пустым
 */

data class Area(
    val parentId: String?,
    val id: String,
    val name: String,
    val areas: List<Area>
)
