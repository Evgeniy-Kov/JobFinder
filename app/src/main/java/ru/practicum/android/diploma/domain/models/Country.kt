package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @param id Хранит идентификатор страны
 * @param name Хранит информацию о названии страны
 */

@Parcelize
data class Country(
    val id: String,
    val name: String
) : Parcelable
