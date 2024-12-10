package ru.practicum.android.diploma.presentation.vacancy_search

import ru.practicum.android.diploma.domain.models.JobItem

fun interface OnItemClickListener {
    fun onItemClick(item: JobItem)
}

fun interface OnItemLongClickListener {
    fun onItemLongClick(item: JobItem)
}
