package ru.practicum.android.diploma.ui.favourites

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ViewVacancyItemBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.getFormattedSalary

class VacancyDetailsViewHolder(
    private val binding: ViewVacancyItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        vacancy: VacancyDetails,
        onItemClickListener: OnItemClickListener?,
    ) {
        binding.root.setOnClickListener {
            onItemClickListener?.onItemClick(vacancy)
        }
        binding.tvVacancyName.text = "${vacancy.name}, ${vacancy.city}"
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

    fun interface OnItemClickListener {
        fun onItemClick(vacancyDetails: VacancyDetails)
    }

}
