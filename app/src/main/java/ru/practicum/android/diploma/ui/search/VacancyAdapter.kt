package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ViewEmptyItemBinding
import ru.practicum.android.diploma.databinding.ViewVacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter : ListAdapter<Vacancy, VacancyViewHolder>(VacancyItemDiffCallBack()) {

    var onItemClickListener: VacancyViewHolder.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            VIEW_TYPE_VACANCY -> {
                ViewVacancyItemBinding.inflate(layoutInflater, parent, false)
            }

            else -> {
                ViewEmptyItemBinding.inflate(layoutInflater, parent, false)
            }
        }
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        if (position > 0) {
            holder.bind(
                getItem(position - 1),
                onItemClickListener
            )
        }
    }

    override fun getItemCount(): Int {
        if (currentList.size == 0) {
            return super.getItemCount()
        } else {
            return currentList.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_EMPTY

            else -> VIEW_TYPE_VACANCY
        }
    }

    companion object {

        const val VIEW_TYPE_VACANCY = 101
        const val VIEW_TYPE_EMPTY = 102
    }

}
