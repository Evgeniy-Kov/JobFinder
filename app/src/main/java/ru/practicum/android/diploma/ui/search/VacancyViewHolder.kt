package ru.practicum.android.diploma.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ViewVacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.getFormattedSalaryForViewHolder
import ru.practicum.android.diploma.util.getVacancyNameForViewHolder

class VacancyViewHolder(private val binding: ViewVacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(vacancy: Vacancy) {

        binding.tvVacancyName.text = getVacancyNameForViewHolder(vacancy.name, vacancy.city)
        binding.tvPlaceOfWork.text = vacancy.employerName
        binding.tvSalary.text = getFormattedSalaryForViewHolder(vacancy.salaryFrom, vacancy.salaryTo, itemView.context)

        Glide.with(itemView)
            .load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.vacancy_cover_placeholder)
            .centerInside()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.dp_12)))
            .into(binding.iconImageView)

    }

}
