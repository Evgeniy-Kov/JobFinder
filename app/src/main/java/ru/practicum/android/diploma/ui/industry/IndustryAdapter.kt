package ru.practicum.android.diploma.ui.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ViewIndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter(private val onSelectedItemCallback: (Industry?) -> Unit) :
    RecyclerView.Adapter<IndustryViewHolder>() {

    private var selectedIndustry: Industry? = null
    var industryList: List<Industry> = emptyList()
        set(value) {
            val oldIndustryList = field
            val newIndustryList = value.toMutableList()

            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldIndustryList.size
                }

                override fun getNewListSize(): Int {
                    return newIndustryList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldIndustryList[oldItemPosition].id == newIndustryList[newItemPosition].id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return oldIndustryList[oldItemPosition] == newIndustryList[newItemPosition]
                }
            })
            field = newIndustryList
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewIndustryItemBinding.inflate(layoutInflater, parent, false)
        return IndustryViewHolder(binding, onSelectedItemCallback) { industry ->
            val oldPosition: Int = selectedIndustry?.let { oldIndustry ->
                industryList.indexOf(oldIndustry)
            } ?: -1
            selectedIndustry = industry
            if (oldPosition >= 0) {
                notifyItemChanged(oldPosition)
            }
            onSelectedItemCallback(selectedIndustry)
        }
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(industryList[position], industryList[position] == selectedIndustry)
    }

    override fun getItemCount(): Int {
        return industryList.size
    }

    fun setSelectedIndustry(industry: Industry?) {
        selectedIndustry = industry
    }
}
