package ru.practicum.android.diploma.ui.region

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ViewAreaItemBinding
import ru.practicum.android.diploma.domain.models.Area

class AreaViewHolder(private val binding: ViewAreaItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        area: Area,
        onItemClickListener: OnItemClickListener?
    ) {
        binding.root.setOnClickListener {
            onItemClickListener?.onItemClick(area)
        }

        binding.regionCountryText.text = area.name
    }

    fun interface OnItemClickListener {
        fun onItemClick(area: Area)
    }

}
