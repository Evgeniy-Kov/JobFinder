package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class JobListAdapter(
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<Vacancy, JobViewHolder>(JobItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_vacancy_item, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(getItem(position)) }
    }

}
