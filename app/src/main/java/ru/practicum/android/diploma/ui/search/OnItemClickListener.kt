package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

fun interface OnItemClickListener {
    fun onItemClick(item: Vacancy)
}
