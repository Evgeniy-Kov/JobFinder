package ru.practicum.android.diploma.ui.industry

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.Industry

class IndustryItemDiffCallBack : DiffUtil.ItemCallback<Industry>() {

    override fun areItemsTheSame(oldItem: Industry, newItem: Industry): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Industry, newItem: Industry): Boolean {
        return oldItem == newItem
    }

}
