package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyItemDiffCallBack : DiffUtil.ItemCallback<Vacancy>() {

    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }

}
