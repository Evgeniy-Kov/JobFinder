package ru.practicum.android.diploma.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ViewVacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsAdapter : ListAdapter<VacancyDetails, VacancyDetailsViewHolder>(VacancyDetailsDiffCallback()) {

    var onItemClickListener: VacancyDetailsViewHolder.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewVacancyItemBinding.inflate(layoutInflater, parent, false)
        return VacancyDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyDetailsViewHolder, position: Int) {
        val vacancyDetails = getItem(position)
        holder.bind(
            vacancyDetails,
            onItemClickListener
        )
    }
}
