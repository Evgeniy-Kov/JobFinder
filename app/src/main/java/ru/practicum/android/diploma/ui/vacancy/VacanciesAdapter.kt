package ru.practicum.android.diploma.ui.vacancy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.domain.models.VacancyDetails


class VacanciesAdapter(private val diffCallback: DiffUtil.ItemCallback<VacancyDetails>) :
    ListAdapter<VacancyDetails, VacancyViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFavouriteBinding.inflate(layoutInflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
