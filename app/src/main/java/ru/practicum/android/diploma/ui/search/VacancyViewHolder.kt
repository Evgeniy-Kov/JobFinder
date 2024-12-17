package ru.practicum.android.diploma.ui.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.getFormattedSalaryForViewHolder
import ru.practicum.android.diploma.util.getVacancyNameForViewHolder

class VacancyViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvVacancyName = itemView.findViewById<TextView>(R.id.tvVacancyName)
    private val tvPlaceOfWork = itemView.findViewById<TextView>(R.id.tvPlaceOfWork)
    private val tvSalary = itemView.findViewById<TextView>(R.id.tvSalary)
    private val iconImageView = itemView.findViewById<ImageView>(R.id.iconImageView)

    fun bind(item: Vacancy) {

        tvVacancyName.text = getVacancyNameForViewHolder(item.name, item.city)
        tvPlaceOfWork.text = item.employerName
        tvSalary.text = getFormattedSalaryForViewHolder(item.salaryFrom, item.salaryTo, itemView.context)

        Glide.with(itemView)
            .load(item.employerLogoUrl)
            .placeholder(R.drawable.vacancy_cover_placeholder)
            .centerCrop()
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.dp_12)))
            .into(iconImageView)

    }

}
