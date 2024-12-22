package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @param id Хранит идентификатор региона
 * @param name Хранит информацию о названии региона
 */

@Parcelize
data class Region(
    val id: String,
    val name: String
) : Parcelable
