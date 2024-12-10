package ru.practicum.android.diploma.presentation.vacancy_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.JobItem
/*
class JobListAdapter(
    private val onItemClickListener: OnItemClickListener,
    private val onItemLongClickListener: OnItemLongClickListener? = null
) : ListAdapter<JobItem, JobViewHolder>(JobItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.track_card, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(getItem(position)) }
        if (onItemLongClickListener != null)
            holder.itemView.setOnLongClickListener {
                onItemLongClickListener.onItemLongClick(getItem(position))
                true
            }
    }

}
*/
