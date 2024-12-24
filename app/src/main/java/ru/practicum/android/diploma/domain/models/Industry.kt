package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @param id Хранит идентификатор сферы деятельности
 * @param industry Хранит массив экземпляров класса `Industry` с информацией о вложенных сферах деятельности
 * @param name Хранит информацию о названии сферы деятельности
 */

@Parcelize
data class Industry(
    val id: String,
    val industry: List<Industry>?,
    val name: String,
) : Parcelable
