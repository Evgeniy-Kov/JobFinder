package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ViewVacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.getFormattedSalary
import ru.practicum.android.diploma.util.getVacancyNameForViewHolder

class VacancyViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        vacancy: Vacancy?,
        onItemClickListener: OnItemClickListener?
    ) {
        if (vacancy != null) {
            (binding as ViewVacancyItemBinding).apply {
                root.setOnClickListener {
                    onItemClickListener?.onItemClick(vacancy)
                }

                binding.tvVacancyName.text = getVacancyNameForViewHolder(vacancy.name, vacancy.city)
                binding.tvPlaceOfWork.text = vacancy.employerName
                binding.tvSalary.text = getFormattedSalary(
                    vacancy.salaryFrom,
                    vacancy.salaryTo,
                    vacancy.currencySymbol,
                    binding.root.context
                )

                Glide.with(itemView)
                    .load(vacancy.employerLogoUrl)
                    .placeholder(R.drawable.vacancy_cover_placeholder)
                    .centerInside()
                    .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.dp_12)))
                    .into(binding.iconImageView)
            }
        }

    }

    fun interface OnItemClickListener {
        fun onItemClick(vacancy: Vacancy)
    }

}
