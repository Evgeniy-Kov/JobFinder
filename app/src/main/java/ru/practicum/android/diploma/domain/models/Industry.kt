package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @param id Хранит идентификатор сферы деятельности
 * @param name Хранит информацию о названии сферы деятельности
 */

@Parcelize
data class Industry(
    val id: String,
    val name: String,
) : Parcelable {
    var isClicked: Boolean = false
}
