package ru.practicum.android.diploma.ui.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ViewIndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter : ListAdapter<Industry, IndustryViewHolder>(IndustryItemDiffCallBack()) {

    var onItemClickListener: IndustryViewHolder.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewIndustryItemBinding.inflate(layoutInflater, parent, false)
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

}
