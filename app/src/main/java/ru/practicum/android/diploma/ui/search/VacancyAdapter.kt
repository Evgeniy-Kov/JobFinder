package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<Vacancy, VacancyViewHolder>(VacancyItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_vacancy_item, parent, false)
        return VacancyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(getItem(position)) }
    }

}
