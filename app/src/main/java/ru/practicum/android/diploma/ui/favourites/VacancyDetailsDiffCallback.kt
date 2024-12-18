package ru.practicum.android.diploma.ui.favourites

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsDiffCallback : DiffUtil.ItemCallback<VacancyDetails>() {
    override fun areItemsTheSame(oldItem: VacancyDetails, newItem: VacancyDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VacancyDetails, newItem: VacancyDetails): Boolean {
        return oldItem == newItem
    }
}
