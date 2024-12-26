package ru.practicum.android.diploma.ui.region

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.Area

class AreaItemDiffCallBack : DiffUtil.ItemCallback<Area>() {

    override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean {
        return oldItem == newItem
    }

}
