package ru.practicum.android.diploma.ui.industry

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ViewIndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(
    val binding: ViewIndustryItemBinding,
    private val onItemClickListener: (Industry) -> Unit,
    private val onRadioClickListener: (Industry) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private val industryRadioButton = itemView.findViewById<MaterialRadioButton>(R.id.industry_radio_button)

    fun bind(industry: Industry, isChecked: Boolean) {
        itemView.setOnClickListener { onItemClickListener(industry) }
        industryRadioButton.isChecked = isChecked
        industryRadioButton.setOnCheckedChangeListener { _, isCheched ->
            if (isCheched) {
                onRadioClickListener(industry)
            }
        }
        industryRadioButton.text = industry.name
    }
}
