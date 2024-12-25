package ru.practicum.android.diploma.ui.industry

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.databinding.ViewIndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(industry: Industry, onItemClickListener: OnItemClickListener?) {
        (binding as ViewIndustryItemBinding).apply {
            root.setOnClickListener {
                onItemClickListener?.onItemClick(industry)
            }
            binding.industryRadioButton.text = industry.name
            binding.industryRadioButton.isChecked = industry.isClicked
        }

    }

    fun interface OnItemClickListener {
        fun onItemClick(industry: Industry)
    }

}
