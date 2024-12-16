package ru.practicum.android.diploma.ui.vacancy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.OnItemClickListener
import ru.practicum.android.diploma.util.UtilityFunctions

class VacancyViewHolder(itemView: View, private val onClickListener: OnItemClickListener) :
    ListItemViewHolder(itemView) {

    private val context = itemView.context
    private val vacancyTitle = itemView.findViewById<TextView>(R.id.tvVacancyName)
    private val companyName = itemView.findViewById<TextView>(R.id.tvPlaceOfWork)
    private val vacancyIcon = itemView.findViewById<ImageView>(R.id.iconImageView)
    private val salaryRange = itemView.findViewById<TextView>(R.id.tvSalary)

    override fun bind(vacancy: Vacancy) {
        itemView.setOnClickListener { onClickListener.onItemClick(vacancy) }
        Glide.with(context)
            .placeholder(R.drawable.vacancy_cover_placeholder)
            .centerInside()
            .load(vacancy.employerLogoUrl)
            .transform(RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.dp_12)))
            .into(vacancyIcon)

        vacancyTitle.text = formatVacancyTitle(vacancy)

        companyName.text = vacancy.employerName

        salaryRange.text = UtilityFunctions.formatSalary(vacancy, context)
    }

    private fun formatVacancyTitle(vacancy: Vacancy): String {
        return String.format(context.getString(R.string.vacancy_template), vacancy.name, vacancy.city)
    }
}
