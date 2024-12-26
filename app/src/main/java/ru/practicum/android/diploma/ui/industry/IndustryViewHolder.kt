package ru.practicum.android.diploma.ui.industry

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ViewIndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(private val binding: ViewIndustryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(industry: Industry, onItemClickListener: OnItemClickListener?, isSelected: Boolean) {
        binding.apply {
            root.setOnClickListener {
                onItemClickListener?.onItemClick(industry)
                (bindingAdapter as? IndustryAdapter)?.selectItem(adapterPosition)
            }
            industryRadioButton.text = industry.name
            industryRadioButton.isChecked = isSelected
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(industry: Industry)
    }
}
