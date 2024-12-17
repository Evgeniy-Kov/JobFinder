package ru.practicum.android.diploma.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(
    private val onVacancyClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    private var vacancies: List<Vacancy> = emptyList()

    fun setData(newVacancies: List<Vacancy>) {
        vacancies = newVacancies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = vacancies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = ItemVacancyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    inner class VacancyViewHolder(
        private val binding: ItemVacancyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(vacancy: Vacancy) {
            binding.apply {
                // Заполните поля данными из вакансии
                name.text = vacancy.name
                vacancyCompany.text = vacancy.company
                vacancyLocation.text = vacancy.location

                // Обработка нажатия на элемент
                root.setOnClickListener {
                    onVacancyClick(vacancy)
                }
            }
        }
    }
}
