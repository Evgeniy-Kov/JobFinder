package ru.practicum.android.diploma.ui.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ViewAreaItemBinding
import ru.practicum.android.diploma.domain.models.Area

class AreaAdapter : ListAdapter<Area, AreaViewHolder>(AreaItemDiffCallBack()) {

    var onItemClickListener: AreaViewHolder.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewAreaItemBinding.inflate(layoutInflater, parent, false)

        return AreaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            onItemClickListener
        )
    }
}
