package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Vacancy

fun interface OnItemClickListener {
    fun onItemClick(vacancy: Vacancy)
}
