package ru.practicum.android.diploma.presentation.vacancy_search

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.JobItem

class JobItemDiffCallBack : DiffUtil.ItemCallback<JobItem>() {

    override fun areItemsTheSame(oldItem: JobItem, newItem: JobItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: JobItem, newItem: JobItem): Boolean {
        return oldItem == newItem
    }

}
